package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;
import com.service.dida.domain.member.entity.Member;

public interface GetMarketUseCase {

    GetMainPageWithoutSoldOut getMainPage(Member member);
}
