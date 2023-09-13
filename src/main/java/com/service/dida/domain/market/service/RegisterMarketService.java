package com.service.dida.domain.market.service;

import static com.service.dida.global.config.exception.errorCode.NftErrorCode.EMPTY_NFT;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.service.dida.domain.market.repository.MarketRepository;
import com.service.dida.domain.market.usecase.RegisterMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MarketErrorCode;
import jakarta.transaction.Transactional;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterMarketService implements RegisterMarketUseCase {

    private final NftRepository nftRepository;
    private final MarketRepository marketRepository;
    private final WalletUseCase walletUseCase;

    private void save(Market market) {
        marketRepository.save(market);
    }

    @Override
    public void registerMarket(Member member, RegisterNftToMarket registerNft)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        walletUseCase.checkPayPwd(wallet,registerNft.getPayPwd());
        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member, registerNft.getNftId())
            .orElseThrow(() -> new BaseException(EMPTY_NFT));
        if(marketRepository.existsByNft(nft).orElse(false)) {
            throw new BaseException(MarketErrorCode.ALREADY_IN_MARKET);
        }
        // 판매하기 시 수수료 및 무료횟수 부분 추가 필요
        checkPrice(registerNft.getPrice());
        Market market = Market.builder()
            .price(registerNft.getPrice())
            .nft(nft)
            .member(member)
            .build();
        save(market);
        nft.changeMarket(market);
    }

    private void checkPrice(double price) {
        if (price > 20000000 || price < 0.01) {
            throw new BaseException(MarketErrorCode.INVALID_PRICE);
        }
    }
}
