package com.service.dida.domain.comment.controller;

import com.service.dida.domain.comment.dto.CommentRequestDto.PostCommentRequestDto;
import com.service.dida.domain.comment.usecase.RegisterCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterCommentController {

    private final RegisterCommentUseCase registerCommentUseCase;

    /**
     * 댓글 생성하기
     * [POST] /common/comments
     */
    @PostMapping("/common/comments")
    public ResponseEntity<Integer> registerComment(
            @CurrentMember Member member,
            @RequestBody @Valid PostCommentRequestDto postCommentRequestDto)
            throws BaseException {
        registerCommentUseCase.registerComment(member, postCommentRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

}
