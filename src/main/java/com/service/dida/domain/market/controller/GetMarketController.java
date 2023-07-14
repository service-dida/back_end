package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;
import com.service.dida.domain.market.dto.MarketResponseDto.GetRecentNft;
import com.service.dida.domain.market.dto.MarketResponseDto.MoreHotMember;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto.NftAndMemberInfo;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetMarketController {
    private final GetMarketUseCase getMarketUseCase;

    /**
     * 메인 화면 가져오기 (Sold Out 제외)
     * [GET] /main
     */
    @GetMapping("/main")
    public ResponseEntity<GetMainPageWithoutSoldOut> getMainPage(@CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMainPage(member), HttpStatus.OK);
    }

    /**
     * 메인 화면 Sold Out 가져오기
     * [GET] /main/sold-out
     */
    @GetMapping("/main/sold-out")
    public ResponseEntity<List<NftAndMemberInfo>> getMainPageSoldOut(
            @CurrentMember Member member, @RequestParam("range") int range)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMainPageSoldOut(member, range, 0, 3), HttpStatus.OK);
    }

    /**
     * Sold out 더보기
     * [GET] /sold-out
     */
    @GetMapping("/sold-out")
    public ResponseEntity<PageResponseDto<List<NftAndMemberInfo>>> getMoreSoldOuts(
            @CurrentMember Member member, @RequestParam("range") int range,
            @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMoreSoldOuts(member, range, pageRequestDto),
                HttpStatus.OK);
    }

    /**
     * Hot Seller 더보기
     * [GET] /hot-sellers
     */
    @GetMapping("/hot-sellers")
    public ResponseEntity<PageResponseDto<List<MoreHotMember>>> getMoreHotSellers(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMoreHotSellers(member, pageRequestDto),
                HttpStatus.OK);
    }

    /**
     * 최신 NFT 더보기
     * [GET] /recent-nfts
     */
    @GetMapping("/recent-nfts")
    public ResponseEntity<PageResponseDto<List<GetRecentNft>>> getMoreRecentNfts(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMoreRecentNfts(member, pageRequestDto),
                HttpStatus.OK);
    }

    /**
     * 활발한 활동 더보기
     * [GET] /hot-members
     */
    @GetMapping("/hot-members")
    public ResponseEntity<PageResponseDto<List<MoreHotMember>>> getMoreHotMembers(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMoreHotMembers(member, pageRequestDto),
                HttpStatus.OK);
    }
}
