package com.service.dida.domain.comment.controller;

import com.service.dida.domain.comment.dto.CommentResponseDto.GetCommentResponseDto;
import com.service.dida.domain.comment.usecase.GetCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetCommentController {

    private final GetCommentUseCase getCommentUseCase;

    /**
     * 게시글 별 댓글 조회
     * [GET] /comments/{postId}
     */
    @GetMapping("/comments/{postId}")
    public ResponseEntity<PageResponseDto<List<GetCommentResponseDto>>> getAllComments(
            @CurrentMember Member member,
            @PathVariable("postId") Long postId,
            @RequestParam("page") int page, @RequestParam("size") int size)
            throws BaseException {
        return new ResponseEntity<>(
                getCommentUseCase.getAllComments(member, postId, new PageRequestDto(page, size)), HttpStatus.OK);
    }
}
