package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetPostUseCase {
    public PostResponseDto.GetPostResponseDto makeGetPostResForm(Long memberId, Post post);
    public PageResponseDto<List<PostResponseDto.GetPostsResponseDto>> getAllPosts(Long memberId, int page);
}
