package com.service.dida.domain.nft.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto.SnsNft;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;
import com.service.dida.domain.nft.dto.NftResponseDto.ProfileNft;
import com.service.dida.domain.nft.usecase.GetNftUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetNftController {
    private final GetNftUseCase getNftUseCase;

    /**
     * NFT 상세 보기 Api
     */
    @GetMapping("/nft/{nftId}")
    public ResponseEntity<NftDetailInfo> getNftDetail(@CurrentMember Member member,
        @PathVariable(value = "nftId") Long nftId) {
        return new ResponseEntity<>(getNftUseCase.getNftDetail(member, nftId), HttpStatus.OK);
    }

    /**
     * 내 NFT 목록 보기 Api
     */
    @GetMapping("/common/profile/nft")
    public ResponseEntity<PageResponseDto<List<ProfileNft>>> getProfileNftList(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(getNftUseCase.getProfileNftList(member, null, pageRequestDto),
            HttpStatus.OK);
    }

    /**
     * 다른유저 NFT 목록 보기 Api
     */
    @GetMapping("/profile/nft/{memberId}")
    public ResponseEntity<PageResponseDto<List<ProfileNft>>> getOtherProfileNftList(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto,
        @PathVariable(name = "memberId") Long memberId) {
        return new ResponseEntity<>(
            getNftUseCase.getProfileNftList(member, memberId, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 내가 소유한 NFT 목록 보기 Api
     */
    @GetMapping("/common/nft/own")
    public ResponseEntity<PageResponseDto<List<SnsNft>>> getMyOwnNftList(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
                getNftUseCase.getMyOwnNftList(member, pageRequestDto), HttpStatus.OK);
    }

}
