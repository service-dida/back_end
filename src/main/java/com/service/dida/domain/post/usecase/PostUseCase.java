package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.EditPostReq;
import com.service.dida.domain.post.dto.PostPostReq;

public interface PostUseCase {
    void save(Post post);
    void createPost(Long memberId, PostPostReq postPostReq);
    boolean checkIsMe(Long memberId, Long ownerId);
    void editPost(Long memberId, EditPostReq editPostReq);
    void deletePost(Long memberId, Long postId);
}
