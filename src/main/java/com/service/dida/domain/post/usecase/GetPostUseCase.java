package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GetPostUseCase {
    PostResponseDto.GetPostResponseDto makeGetPostResForm(Long memberId, Post post, boolean comment);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> makePostListForm(Long memberId, Page<Post> posts, boolean comment);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> getAllPosts(Long memberId, PageRequestDto pageRequestDto);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> getPostsByNftId(Long memberId, Long nftId, PageRequestDto pageRequestDto);
    PostResponseDto.GetPostResponseDto getPost(Long memberId, Long postId);
}
