package com.service.dida.domain.wallet.service;

import static com.service.dida.global.config.constants.ServerConstants.PRIVATE_KEY;
import static com.service.dida.global.config.constants.ServerConstants.PURCHASE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SEND_KLAY_OUTSIDE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SEND_NFT_OUTSIDE_FEE;
import static com.service.dida.global.config.constants.ServerConstants.SWAP_FEE;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.service.RegisterMemberService;
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
import com.service.dida.domain.wallet.dto.WalletResponseDto.WalletDetail;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.util.usecase.BcryptUseCase;
import com.service.dida.global.util.usecase.KasUseCase;
import com.service.dida.global.util.usecase.RsaUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {

    private final WalletRepository walletRepository;
    private final RegisterMemberService registerMemberService;
    private final NftRepository nftRepository;
    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final KasUseCase kasUseCase;
    private final BcryptUseCase bcryptUseCase;
    private final RsaUseCase rsaUseCase;

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
        registerMemberService.save(member);
    }

    @Override
    public WalletDetail getWalletDetail(Member member)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        return new WalletDetail(wallet.getAddress(), kasUseCase.getKlay(wallet),
            kasUseCase.getDida(wallet));
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
    public void checkPayPwd(Wallet wallet, String encodedPwd)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        if (wallet.getWrongCnt() == 5) {
            throw new BaseException(WalletErrorCode.FIVE_ERRORS_FOR_PWD);
        }
        if (bcryptUseCase.isMatch(rsaUseCase.rsaDecode(encodedPwd, PRIVATE_KEY),
            wallet.getPayPwd())) {
            wallet.initWrongCnt();
        } else {
            wallet.upWrongCnt();
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
        save(wallet);
    }

    @Override
    public void registerWallet(Member member, CheckPwd checkPwd)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        if (checkPassword(checkPwd)) {
            register(member,
                bcryptUseCase.encrypt(rsaUseCase.rsaDecode(checkPwd.getPayPwd(), PRIVATE_KEY)));
        } else {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }

    @Override
    public void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        checkPayPwd(wallet, changeCoin.getPayPwd());
        useWallet(wallet);
        checkKlay(wallet, changeCoin.getCoin());
        exchangeKlay(member, changeCoin.getCoin());
    }

    @Override
    public void swapDidaToKlay(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        checkPayPwd(wallet, changeCoin.getPayPwd());
        useWallet(wallet);
        checkDida(wallet, changeCoin.getCoin());
        exchangeDida(member, changeCoin.getCoin());
    }

    @Override
    public void sendKlayOutside(Member member, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        checkForSendKlayOutside(wallet, sendKlayOutside);
        sendKlayOutsideFun(member, sendKlayOutside);
    }

    @Override
    public void sendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        checkSendNftOutside(member, sendNftRequestDto);

        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member,
                sendNftRequestDto.getNftId())
            .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        sendNftOutsideFun(member, nft, sendNftRequestDto);
    }

    @Override
    public void purchaseNftInMarket(Member buyer, String payPwd, Market market)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Member seller = market.getMember();
        checkForDeal(buyer.getWallet(), payPwd, market.getPrice());
        purchaseNftOnMarketFun(buyer, seller, market);
    }

    private void checkForDeal(Wallet buyerWallet, String payPwd, double price)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
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
                market.getPrice() - PURCHASE_FEE, nft,
                new TransactionSetDto(sendDida, sendNft, sendFee))
        );
        nft.changeMember(buyer);
        nft.changeMarket(null);
        market.delete();
    }

    private void checkSendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
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
            new MintingTransactionDto(member.getMemberId(), nft,
                new TransactionSetDto(sendNft, null, sendFee)));
        nft.setDeleted();
    }

    private void checkForSendKlayOutside(Wallet wallet, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
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

    private void checkWallet(Wallet wallet, String payPwd)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        checkPayPwd(wallet, payPwd);
        useWallet(wallet);
    }

    private boolean checkPassword(CheckPwd checkPwd)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return rsaUseCase.rsaDecode(checkPwd.getPayPwd(), PRIVATE_KEY)
            .equals(rsaUseCase.rsaDecode(checkPwd.getCheckPwd(), PRIVATE_KEY));
    }
}
