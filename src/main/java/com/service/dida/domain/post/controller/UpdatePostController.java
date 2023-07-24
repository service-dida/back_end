package com.service.dida.domain.post.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.dto.EditPostRequestDto;
import com.service.dida.domain.post.usecase.UpdatePostUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
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
     * [PATCH] /common/posts
     */
    @PatchMapping("/common/posts")
    public ResponseEntity<Integer> editPost(
            @RequestBody @Valid EditPostRequestDto editPostRequestDto,
            @CurrentMember Member member)
            throws BaseException {
        updatePostUseCase.editPost(member, editPostRequestDto);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }

    /**
     * 게시글 삭제하기
     * [DELETE] /common/posts/{postId}
     */
    @DeleteMapping("/common/posts/{postId}")
    public ResponseEntity<Integer> deletePost(
            @PathVariable("postId") Long postId, @CurrentMember Member member)
            throws BaseException {
        updatePostUseCase.deletePost(member, postId);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }
}
