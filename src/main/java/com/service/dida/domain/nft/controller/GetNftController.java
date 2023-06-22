package com.service.dida.domain.nft.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;
import com.service.dida.domain.nft.usecase.GetNftUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
