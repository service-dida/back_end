package com.service.dida.domain.comment.controller;

import com.service.dida.domain.comment.usecase.UpdateCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateCommentController {

    private final UpdateCommentUseCase updateCommentUseCase;

    /**
     * 댓글 삭제하기
     * [PATCH] /common/comment/delete
     */
    @PatchMapping("/common/comment/delete")
    public ResponseEntity<Integer> deleteComment(
            @CurrentMember Member member,
            @RequestParam("commentId") Long commentId)
            throws BaseException {
        updateCommentUseCase.deleteComment(member, commentId);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }
}
