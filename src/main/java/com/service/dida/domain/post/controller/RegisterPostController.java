package com.service.dida.domain.post.controller;


import com.service.dida.domain.post.dto.PostPostRequestDto;
import com.service.dida.domain.post.service.RegisterPostService;
import com.service.dida.global.config.exception.BaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterPostController {
    private final RegisterPostService registerPostService;

    /**
     * 게시글 생성하기
     * [POST] /post
     */
    @PostMapping("/post")
    public ResponseEntity<Integer> createPost(
            @RequestBody @Valid PostPostRequestDto postPostRequestDto)
            throws BaseException {
        Long memberId = 0L;
        registerPostService.createPost(memberId, postPostRequestDto);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }

}
