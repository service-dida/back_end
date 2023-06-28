package com.service.dida.domain.hide.member_hide.controller;

import com.service.dida.domain.hide.member_hide.usecase.RegisterMemberHideUseCase;
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
public class RegisterMemberHideController {

    private final RegisterMemberHideUseCase registerMemberHideUseCase;

    /**
     * Member 숨기기
     * [POST] /common/member/hide
     */
    @PostMapping("/common/member/hide")
    public ResponseEntity<Integer> hideMember(
            @CurrentMember Member member, @RequestParam("nftId") Long memberId)
            throws BaseException {
        registerMemberHideUseCase.hideMember(member, memberId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
