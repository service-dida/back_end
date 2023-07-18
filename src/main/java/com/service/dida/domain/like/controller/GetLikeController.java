package com.service.dida.domain.like.controller;

import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetLikeController {

    private final GetLikeUseCase getLikeUseCase;

    /**
     * 내가 좋아요한 NFT 목록 보기 Api
     */
    @GetMapping("/common/nft/like")
    public ResponseEntity<PageResponseDto<List<NftResponseDto.SnsNft>>> getMyLikeNftList(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
                getLikeUseCase.getMyLikeNftList(member, pageRequestDto), HttpStatus.OK);
    }

}
