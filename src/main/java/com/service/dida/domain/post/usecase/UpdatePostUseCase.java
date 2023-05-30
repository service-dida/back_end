package com.service.dida.domain.post.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.dto.EditPostRequestDto;

public interface UpdatePostUseCase {

    void editPost(Member member, EditPostRequestDto editPostRequestDto);
    void deletePost(Member member, Long postId);
}
