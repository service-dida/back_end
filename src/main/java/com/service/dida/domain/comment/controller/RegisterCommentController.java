package com.service.dida.domain.comment.controller;

import com.service.dida.domain.comment.dto.CommentRequestDto.PostCommentRequestDto;
import com.service.dida.domain.comment.service.RegisterCommentService;
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

    private final RegisterCommentService registerCommentService;

    /**
     * 댓글 생성하기
     * [POST] /comment
     */
    @PostMapping("comment")
    public ResponseEntity<Integer> registerComment(
            @CurrentMember Member member,
            @RequestBody @Valid PostCommentRequestDto postCommentRequestDto)
            throws BaseException {
        registerCommentService.registerComment(member, postCommentRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

}
