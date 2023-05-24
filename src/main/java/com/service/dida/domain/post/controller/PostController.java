package com.service.dida.domain.post.controller;


import com.service.dida.domain.post.dto.EditPostReq;
import com.service.dida.domain.post.dto.PostPostReq;
import com.service.dida.domain.post.service.PostService;
import com.service.dida.global.config.exception.BaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /** 게시글 생성하기
     *  [POST] /post
     */
    @PostMapping("/post")
    public ResponseEntity<Integer> createPost(@RequestBody @Valid PostPostReq postPostReq) throws BaseException {
        Long userId = 0L;
        postService.createPost(userId, postPostReq);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }

    /** 게시글 수정하기
     *  [PATCH] /post
     */
    @PatchMapping("/post")
    public ResponseEntity<Integer> editPost(@RequestBody @Valid EditPostReq editPostReq) {
        Long userId = 0L;
        postService.editPost(userId, editPostReq);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }
}
