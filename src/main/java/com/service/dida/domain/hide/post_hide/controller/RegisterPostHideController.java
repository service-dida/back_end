package com.service.dida.domain.hide.post_hide.controller;

import com.service.dida.domain.hide.post_hide.usecase.RegisterPostHideUseCase;
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
public class RegisterPostHideController {

    private final RegisterPostHideUseCase registerPostHideUseCase;
    /**
     * 게시글 숨기기
     * [POST] /common/post/hide
     */
    @PostMapping("/common/post/hide")
    public ResponseEntity<Integer> hidePost(
            @RequestParam("postId") Long postId, @CurrentMember Member member)
            throws BaseException {
        registerPostHideUseCase.hidePost(member, postId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
