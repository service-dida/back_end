package com.service.dida.domain.post.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.dto.PostPostRequestDto;

public interface RegisterPostUseCase {
    void createPost(Member member, PostPostRequestDto postPostRequestDto);
}
