package com.service.dida.domain.post.usecase;

import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.EditPostReq;
import com.service.dida.domain.post.dto.PostPostReq;

public interface PostUseCase {
    void save(Post post);
    void createPost(Long userId, PostPostReq postPostReq);
    boolean checkIsMe(Long userId, Long ownerId);
    void editPost(Long userId, EditPostReq editPostReq);
    void deletePost(Long userId, Long postId);
}
