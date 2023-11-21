package com.service.dida.domain.market.service;

import com.service.dida.domain.alarm.usecase.RegisterAlarmUseCase;
import com.service.dida.domain.market.Market;
import com.service.dida.domain.market.dto.MarketRequestDto.UpdateMarket;
import com.service.dida.domain.market.repository.MarketRepository;
import com.service.dida.domain.market.usecase.UpdateMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MarketErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMarketService implements UpdateMarketUseCase {

    private final MarketRepository marketRepository;
    private final WalletUseCase walletUseCase;
    private final RegisterAlarmUseCase registerAlarmUseCase;

    @Override
    public void deleteMarket(Member member, UpdateMarket updateMarket)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        walletUseCase.checkPayPwd(member.getWallet(),updateMarket.getPayPwd());
        Market market = marketRepository.findMarketByMarketIdWithDeleted(updateMarket.getMarketId())
            .orElseThrow(() -> new BaseException(MarketErrorCode.EMPTY_MARKET));
        if (!Objects.equals(market.getMember().getMemberId(), member.getMemberId())) {
            // proxy 때문에 객체비교는 불가
            throw new BaseException(MarketErrorCode.INVALID_MEMBER);
        }
        market.delete();
        market.getNft().changeMarket(null);
    }

    @Override
    public void purchaseNftInMarket(Member buyer, UpdateMarket updateMarket)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Market market = marketRepository.findMarketByMarketIdWithDeleted(updateMarket.getMarketId())
            .orElseThrow(() -> new BaseException(MarketErrorCode.EMPTY_MARKET));
        if (Objects.equals(market.getMember().getMemberId(), buyer.getMemberId())) {
            throw new BaseException(MarketErrorCode.ITS_YOUR_MARKET);
        }
        walletUseCase.purchaseNftInMarket(buyer, updateMarket.getPayPwd(), market);
        registerAlarmUseCase.registerSaleAlarm(market.getMember(),market.getNft().getNftId());
    }
}
