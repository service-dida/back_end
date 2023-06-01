package com.service.dida.domain.wallet.service;

import static com.service.dida.global.config.constants.ServerConstants.SWAP_FEE;

import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.domain.transaction.usecase.TransactionUseCase;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletResponseDto;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.util.usecase.KasUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {

    private final MemberRepository memberRepository;
    private final WalletRepository walletRepository;
    private final TransactionUseCase transactionUseCase;
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
            .member(member)
            .build();
        save(wallet);
        member.changeRole(Role.ROLE_MEMBER);
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
    public WalletResponseDto isExistWallet(Long memberId) {
        return new WalletResponseDto(
            walletRepository.existsWalletByMember(memberRepository.findByMemberId(memberId).get())
                .orElse(false));
    }

    @Override
    public void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        wallet.checkPayPwd(changeCoin.getPayPwd());
        wallet.useWallet();
        checkKlay(member.getWallet(), changeCoin.getCoin());
        exchangeKlay(wallet, changeCoin.getCoin());
    }

    private void exchangeKlay(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException {
        String sendKlay = kasUseCase.sendKlayToLiquidPool(wallet, coin - SWAP_FEE);
        String receiveDida = kasUseCase.mintDida(wallet, coin - SWAP_FEE);
        String sendFee = kasUseCase.sendKlayToFeeAccount(wallet, SWAP_FEE);
        transactionUseCase.saveSwapKlayToDidaTransaction(
            new SwapTransactionDto(wallet.getMember().getMemberId(), coin - SWAP_FEE,
                new TransactionSetDto(sendKlay, receiveDida, sendFee)));
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
