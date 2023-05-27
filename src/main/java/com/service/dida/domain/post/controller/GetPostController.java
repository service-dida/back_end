package com.service.dida.domain.post.controller;

import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostResponseDto;
import com.service.dida.domain.post.service.GetPostService;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetPostController {

    private final GetPostService getPostService;

    /**
     * 게시글 전체 조회하기
     * [GET] /posts
     */
    @GetMapping("/posts")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostResponseDto>>> getAllPosts(
            @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        Long memberId = 0L;
        return new ResponseEntity<>(getPostService.getAllPosts(memberId, pageRequestDto), HttpStatus.OK);
    }

    /**
     * NFT 별 게시글 최신 조회하기
     * [GET] /posts/{nftId}
     */
    @GetMapping("/posts/{nftId}")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostResponseDto>>> getPostsByNft(
            @PathVariable("nftId") Long nftId, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        Long memberId = 0L;
        return new ResponseEntity<>(getPostService.getPostsByNftId(memberId, nftId, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회하기
     * [GET] /post/{postId}
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto.GetPostResponseDto> getPost(@PathVariable("postId") Long postId)
            throws BaseException {
        Long memberId = 0L;
        return new ResponseEntity<>(getPostService.getPost(memberId, postId), HttpStatus.OK);
    }
}
