package com.service.dida.domain.post.controller;

import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostResponseDto;
import com.service.dida.domain.post.service.GetPostService;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetPostController {

    private final GetPostService getPostService;

    /**
     * 게시글 전체 조회하기
     * [GET] /posts/{page}
     */
    @GetMapping("/posts/{page}")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostsResponseDto>>> getAllPosts(
            @PathVariable("page") int page)
            throws BaseException {
        Long memberId = 0L;
        return new ResponseEntity<>(getPostService.getAllPosts(memberId, page), HttpStatus.OK);
    }
}
