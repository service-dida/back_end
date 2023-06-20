package com.service.dida.domain.market.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateMarketUseCase {

    void deleteMarket(Member member,Long marketId);
}
