package com.service.dida.domain.wallet.service;

import static com.service.dida.global.config.constants.ServerConstants.SEND_KLAY_OUTSIDE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SWAP_FEE;

import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.TransactionType;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SendKlayOutsideTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.domain.transaction.usecase.RegisterTransactionUseCase;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendKlayOutside;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.util.usecase.KasUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {

    private final WalletRepository walletRepository;
    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final KasUseCase kasUseCase;

    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public void register(Member member, String payPwd)
        throws IOException, ParseException, InterruptedException {
        String address = kasUseCase.createAccount();
        Wallet wallet = Wallet.builder()
            .address(address)
            .payPwd(payPwd)
            .wrongCnt(0)
            .build();
        save(wallet);
        member.changeRole(Role.ROLE_MEMBER);
        member.updateWallet(wallet);
    }

    @Override
    public void useWallet(Wallet wallet) {
        if (Duration.between(wallet.getUpdatedAt(), LocalDateTime.now()).getSeconds() < 180) {
            throw new BaseException(WalletErrorCode.FAILED_USE_WALLET);
        } else {
            wallet.updateEntity();
            save(wallet);
        }
    }

    @Override
    public void registerWallet(Member member, CheckPwd checkPwd)
        throws IOException, ParseException, InterruptedException {
        if (checkPassword(checkPwd)) {
            register(member, checkPwd.getPayPwd());
        } else {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }

    @Override
    public void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        wallet.checkPayPwd(changeCoin.getPayPwd());
        useWallet(wallet);
        checkKlay(wallet, changeCoin.getCoin());
        exchangeKlay(member, changeCoin.getCoin());
    }

    @Override
    public void swapDidaToKlay(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        wallet.checkPayPwd(changeCoin.getPayPwd());
        useWallet(wallet);
        checkDida(wallet, changeCoin.getCoin());
        exchangeDida(member, changeCoin.getCoin());
    }

    @Override
    public void sendKlayOutside(Member member, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        checkForSendKlayOutside(wallet, sendKlayOutside);
        sendKlayOutsideFun(member, sendKlayOutside);
    }

    private void checkForSendKlayOutside(Wallet wallet, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException {
        if (walletRepository.existsWalletByAddress(sendKlayOutside.getAddress()).orElse(false)) {
            throw new BaseException(WalletErrorCode.IN_MEMBER_ADDRESS);
        }
        wallet.checkPayPwd(sendKlayOutside.getChangeCoin().getPayPwd());
        useWallet(wallet);
        checkKlay(wallet, sendKlayOutside.getChangeCoin().getCoin());
    }

    private void sendKlayOutsideFun(Member member, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        String sendKlay = kasUseCase.sendKlayOutside(wallet.getAddress(),
            sendKlayOutside.getAddress(), sendKlayOutside.getChangeCoin().getCoin());
        String sendFee = "";
        if (SEND_KLAY_OUTSIDE_FEE != 0D) {
            sendFee = kasUseCase.sendDidaToFeeAccount(wallet, SEND_KLAY_OUTSIDE_FEE);
        }
        registerTransactionUseCase.saveSendKlayOutsideTransaction(
            new SendKlayOutsideTransactionDto(member.getMemberId(),
                sendKlayOutside.getChangeCoin().getCoin(),
                new TransactionSetDto(sendKlay, null, sendFee)));
    }


    private void exchangeDida(Member member, double coin)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        String sendDida = kasUseCase.sendDidaToLiquidPool(wallet, coin - SWAP_FEE);
        String receiveKlay = kasUseCase.sendKlayFromLiquidPoolToUser(wallet, coin - SWAP_FEE);
        String sendFee = "";
        if (SWAP_FEE != 0D) {
            sendFee = kasUseCase.sendDidaToFeeAccount(wallet, SWAP_FEE);
        }
        registerTransactionUseCase.saveSwapTransaction(TransactionType.SWAP2,
            new SwapTransactionDto(member.getMemberId(), coin - SWAP_FEE,
                new TransactionSetDto(sendDida, receiveKlay, sendFee)));
    }

    private void exchangeKlay(Member member, double coin)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        String sendKlay = kasUseCase.sendKlayToLiquidPool(wallet, coin - SWAP_FEE);
        String receiveDida = kasUseCase.mintDida(wallet, coin - SWAP_FEE);
        String sendFee = "";
        if (SWAP_FEE != 0D) {
            sendFee = kasUseCase.sendKlayToFeeAccount(wallet, SWAP_FEE);
        }
        registerTransactionUseCase.saveSwapTransaction(TransactionType.SWAP1,
            new SwapTransactionDto(member.getMemberId(), coin - SWAP_FEE,
                new TransactionSetDto(sendKlay, receiveDida, sendFee)));
    }

    private void checkDida(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException {
        if (kasUseCase.getDida(wallet) < coin - SWAP_FEE) {
            throw new BaseException(WalletErrorCode.NOT_ENOUGH_COIN);
        }
    }

    private void checkKlay(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException {
        if (kasUseCase.getKlay(wallet) < coin - SWAP_FEE) {
            throw new BaseException(WalletErrorCode.NOT_ENOUGH_COIN);
        }
    }

    private boolean checkPassword(CheckPwd checkPwd) {
        return checkPwd.getPayPwd().equals(checkPwd.getCheckPwd());
    }
}
