package com.service.dida.domain.hide.comment_hide.controller;

import com.service.dida.domain.hide.comment_hide.usecase.RegisterCommentHideUseCase;
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
public class RegisterCommentHideController {

    private final RegisterCommentHideUseCase registerCommentHideUseCase;

    /**
     * 댓글 숨기기
     * [POST] /common/comment/hide
     */
    @PostMapping("/common/comment/hide")
    public ResponseEntity<Integer> hideComment(
            @RequestParam("commentId") Long commentId, @CurrentMember Member member)
            throws BaseException {
        registerCommentHideUseCase.hideComment(member, commentId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
