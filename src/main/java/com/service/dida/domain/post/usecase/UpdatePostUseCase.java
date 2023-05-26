package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.dto.EditPostRequestDto;

public interface UpdatePostUseCase {

    void editPost(Long memberId, EditPostRequestDto editPostRequestDto);
    void deletePost(Long memberId, Long postId);
}
