package com.service.dida.domain.comment.repository;

import com.service.dida.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deleted = false")
    Page<Comment> findByPostIdWithDeleted(Long postId, PageRequest pageRequest);

    @Query(value = "SELECT c FROM Comment c WHERE c.deleted = false")
    Optional<Comment> findByCommentIdWithDeleted(Long postId);
}
