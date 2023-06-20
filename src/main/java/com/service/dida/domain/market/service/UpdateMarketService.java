package com.service.dida.domain.market.service;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.market.repository.MarketRepository;
import com.service.dida.domain.market.usecase.UpdateMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MarketErrorCode;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMarketService implements UpdateMarketUseCase {

    private final MarketRepository marketRepository;

    @Override
    public void deleteMarket(Member member, Long marketId) {
        Market market = marketRepository.findMarketByMarketIdWithDeleted(marketId)
            .orElseThrow(() -> new BaseException(MarketErrorCode.EMPTY_MARKET));
        if (!Objects.equals(market.getMember().getMemberId(), member.getMemberId())) { // proxy 때문에 객체비교는 불가
            throw new BaseException(MarketErrorCode.INVALID_MEMBER);
        }
        market.delete();
    }
}
