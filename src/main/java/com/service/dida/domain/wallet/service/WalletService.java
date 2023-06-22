package com.service.dida.domain.wallet.service;

import static com.service.dida.global.config.constants.ServerConstants.PURCHASE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SEND_KLAY_OUTSIDE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SEND_NFT_OUTSIDE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SWAP_FEE;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.transaction.TransactionType;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.PurchaseNftOnMarketTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SendKlayOutsideTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.domain.transaction.usecase.RegisterTransactionUseCase;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendKlayOutside;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendNftRequestDto;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
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
    private final NftRepository nftRepository;
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

    @Override
    public void sendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        checkSendNftOutside(member, sendNftRequestDto);

        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member,
                sendNftRequestDto.getNftId())
            .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        sendNftOutsideFun(member, nft, sendNftRequestDto);
    }

    @Override
    public void purchaseNftInMarket(Member buyer, String payPwd, Market market)
        throws IOException, ParseException, InterruptedException {
        Member seller = market.getMember();
        checkForDeal(buyer.getWallet(), payPwd, market.getPrice());
        purchaseNftOnMarketFun(buyer, seller, market);
    }

    private void checkForDeal(Wallet buyerWallet, String payPwd, double price)
        throws IOException, ParseException, InterruptedException {
        checkWallet(buyerWallet, payPwd);
        checkDida(buyerWallet, price);
    }

    private void purchaseNftOnMarketFun(Member buyer, Member seller, Market market)
        throws IOException, ParseException, InterruptedException {
        Nft nft = market.getNft();
        Wallet buyerWallet = buyer.getWallet();
        Wallet sellerWallet = seller.getWallet();
        String sendFee = null;
        if (PURCHASE_FEE != 0D) {
            sendFee = kasUseCase.sendDidaToFeeAccount(buyerWallet, PURCHASE_FEE);
        }
        String sendDida = kasUseCase.sendDidaToSeller(buyerWallet, sellerWallet,
            market.getPrice() - PURCHASE_FEE);
        String sendNft = kasUseCase.sendNft(sellerWallet, buyerWallet, nft);
        registerTransactionUseCase.savePurchaseNftOnMarketTransaction(
            new PurchaseNftOnMarketTransactionDto(buyer.getMemberId(), seller.getMemberId(),
                market.getPrice() - PURCHASE_FEE, nft.getNftId(),
                new TransactionSetDto(sendDida, sendNft, sendFee))
        );
        nft.changeMember(buyer);
        market.delete();
    }

    private void checkSendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        if (walletRepository.existsWalletByAddress(sendNftRequestDto.getAddress()).orElse(false)) {
            throw new BaseException(WalletErrorCode.IN_MEMBER_ADDRESS);
        }
        checkWallet(member.getWallet(), sendNftRequestDto.getPayPwd());
        checkDida(member.getWallet(), SEND_NFT_OUTSIDE_FEE);
    }

    private void sendNftOutsideFun(Member member, Nft nft, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        String sendNft = kasUseCase.sendNftOutside(member.getWallet().getAddress(),
            sendNftRequestDto.getAddress(), nft.getId());
        String sendFee = "";
        if (SEND_NFT_OUTSIDE_FEE != 0D) {
            sendFee = kasUseCase.sendDidaToFeeAccount(wallet, SEND_NFT_OUTSIDE_FEE);
        }
        registerTransactionUseCase.saveSendNftOutsideTransaction(
            new MintingTransactionDto(member.getMemberId(), nft.getNftId(),
                new TransactionSetDto(sendNft, null, sendFee)));
        nft.setDeleted();
    }

    private void checkForSendKlayOutside(Wallet wallet, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException {
        if (walletRepository.existsWalletByAddress(sendKlayOutside.getAddress()).orElse(false)) {
            throw new BaseException(WalletErrorCode.IN_MEMBER_ADDRESS);
        }
        checkWallet(wallet, sendKlayOutside.getChangeCoin().getPayPwd());
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
        if (kasUseCase.getDida(wallet) < coin) {
            throw new BaseException(WalletErrorCode.NOT_ENOUGH_COIN);
        }
    }

    private void checkKlay(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException {
        if (kasUseCase.getKlay(wallet) < coin) {
            throw new BaseException(WalletErrorCode.NOT_ENOUGH_COIN);
        }
    }

    private void checkWallet(Wallet wallet, String payPwd) {
        wallet.checkPayPwd(payPwd);
        useWallet(wallet);
    }

    private boolean checkPassword(CheckPwd checkPwd) {
        return checkPwd.getPayPwd().equals(checkPwd.getCheckPwd());
    }
}
