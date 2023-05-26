package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.dto.PostPostRequestDto;

public interface RegisterPostUseCase {
    void createPost(Long memberId, PostPostRequestDto postPostRequestDto);
}
