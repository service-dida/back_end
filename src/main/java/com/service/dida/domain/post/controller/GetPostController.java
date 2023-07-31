package com.service.dida.domain.post.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.usecase.GetPostUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetPostController {

    private final GetPostUseCase getPostUseCase;

    /**
     * 게시글 전체 조회하기
     * [GET] /posts
     */
    @GetMapping("/posts")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostResponseDto>>> getAllPosts(
            @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getAllPosts(member,
                new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * NFT 별 게시글 최신 조회하기
     * [GET] /posts/nft/{nftId}
     */
    @GetMapping("/posts/nft/{nftId}")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostResponseDto>>> getPostsByNft(
            @PathVariable("nftId") Long nftId, @RequestParam("page") int page, @RequestParam("size") int size,
            @CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getPostsByNftId(member, nftId,
                new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회하기
     * [GET] /posts/{postId}
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto.GetPostResponseDto> getPost(
            @PathVariable("postId") Long postId, @CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getPost(member, postId), HttpStatus.OK);
    }

    /**
     * 시끌벅적 게시판 조회하기
     * [GET] /posts/hot
     */
    @GetMapping("/posts/hot")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetHotPosts>>> getHotPosts(
            @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getHotPosts(member,
                new PageRequestDto(page, size)), HttpStatus.OK);
    }
}
