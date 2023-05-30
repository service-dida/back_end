package com.service.dida.domain.hide.controller;

import com.service.dida.domain.hide.usecase.RegisterHideUseCase;
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
public class RegisterHideController {
    private final RegisterHideUseCase registerHideUseCase;

    /**
     * NFT 숨기기
     * [POST] /nft/hide
     */
    @PostMapping("/nft/hide")
    public ResponseEntity<Integer> hideCard(
            @RequestParam("nftId") Long nftId, @CurrentMember Member member)
            throws BaseException {
        registerHideUseCase.hideCard(member, nftId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
