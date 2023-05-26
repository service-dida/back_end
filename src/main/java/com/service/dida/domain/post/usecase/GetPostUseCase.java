package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetPostUseCase {
    PostResponseDto.GetPostResponseDto makeGetPostResForm(Long memberId, Post post);
    PageResponseDto<List<PostResponseDto.GetPostsResponseDto>> getAllPosts(Long memberId, PageRequestDto pageRequestDto);
    PageResponseDto<List<PostResponseDto.GetPostsResponseDto>> getPostsByNftId(Long memberId, Long nftId, PageRequestDto pageRequestDto);
}
