package com.service.dida.domain.post.controller;

import com.service.dida.domain.post.dto.EditPostRequestDto;
import com.service.dida.domain.post.usecase.UpdatePostUseCase;
import com.service.dida.global.config.exception.BaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UpdatePostController {

    private final UpdatePostUseCase updatePostUseCase;

    /**
     * 게시글 수정하기
     * [PATCH] /post
     */
    @PatchMapping("/post")
    public ResponseEntity<Integer> editPost(
            @RequestBody @Valid EditPostRequestDto editPostRequestDto)
            throws BaseException {
        Long memberId = 0L;
        updatePostUseCase.editPost(memberId, editPostRequestDto);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }

    /**
     * 게시글 삭제하기
     * [PATCH] /post/delete
     */
    @PatchMapping("/post/delete")
    public ResponseEntity<Integer> deletePost(
            @RequestParam("postId") Long postId)
            throws BaseException {
        Long memberId = 0L;
        updatePostUseCase.deletePost(memberId, postId);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }
}
