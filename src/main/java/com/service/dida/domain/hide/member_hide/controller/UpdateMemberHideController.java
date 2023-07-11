package com.service.dida.domain.hide.member_hide.controller;

import com.service.dida.domain.hide.member_hide.usecase.UpdateMemberHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMemberHideController {

    private final UpdateMemberHideUseCase updateMemberHideUseCase;

    /**
     * Member 숨기기 취소
     * [DELETE] /common/member/hide
     */
    @DeleteMapping("/common/member/hide")
    public ResponseEntity<Integer> unhideMember(
            @RequestParam("memberId") Long memberId, @CurrentMember Member member)
            throws BaseException {
        updateMemberHideUseCase.unhideMember(member, memberId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
