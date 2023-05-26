package com.service.dida.domain.post.repository;

import com.service.dida.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.postId = :postId and p.deleted = false")
    Optional<Post> findByPostIdWithDeleted(Long postId);
    Optional<Post> findByPostId(Long postId);

    @Query(value = "SELECT p FROM Post p WHERE p.deleted = false")
    Page<Post> findAllWithDeleted(PageRequest pageRequest);
}
