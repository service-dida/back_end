package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketResponseDto;
import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetMarketUseCase {

    GetMainPageWithoutSoldOut getMainPage(Member member);
    PageResponseDto<List<MarketResponseDto.MoreHotSeller>> getMoreHotSellers(Member member, PageRequestDto pageRequestDto);
    PageResponseDto<List<MarketResponseDto.GetRecentNft>> getMoreRecentNfts(Member member, PageRequestDto pageRequestDto);
    PageResponseDto<List<MarketResponseDto.MoreHotSeller>> getMoreHotMembers(Member member, PageRequestDto pageRequestDto);
}
