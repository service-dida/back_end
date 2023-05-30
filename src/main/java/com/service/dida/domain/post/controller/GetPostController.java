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
import org.springframework.web.bind.annotation.RequestBody;
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
            @RequestBody PageRequestDto pageRequestDto, @CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getAllPosts(member, pageRequestDto), HttpStatus.OK);
    }

    /**
     * NFT 별 게시글 최신 조회하기
     * [GET] /posts/{nftId}
     */
    @GetMapping("/posts/{nftId}")
    public ResponseEntity<PageResponseDto<List<PostResponseDto.GetPostResponseDto>>> getPostsByNft(
            @PathVariable("nftId") Long nftId, @RequestBody PageRequestDto pageRequestDto,
            @CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getPostsByNftId(member, nftId, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회하기
     * [GET] /post/{postId}
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto.GetPostResponseDto> getPost(
            @PathVariable("postId") Long postId, @CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getPostUseCase.getPost(member, postId), HttpStatus.OK);
    }
}
