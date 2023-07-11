package com.service.dida.domain.hide.nft_hide.controller;

import com.service.dida.domain.hide.nft_hide.usecase.RegisterNftHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterNftHideController {
    private final RegisterNftHideUseCase registerNftHideUseCase;

    /**
     * NFT 숨기기
     * [POST] /common/nft/hide
     */
    @PostMapping("/common/nft/hide")
    public ResponseEntity<Integer> hideNft(
            @RequestParam("nftId") Long nftId, @CurrentMember Member member)
            throws BaseException {
        registerNftHideUseCase.hideNft(member, nftId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
