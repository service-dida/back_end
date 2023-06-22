package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.service.dida.domain.member.entity.Member;

public interface RegisterMarketUseCase {

    void registerMarket(Member member, RegisterNftToMarket registerNft);
}
