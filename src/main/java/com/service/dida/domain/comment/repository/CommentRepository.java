package com.service.dida.domain.comment.repository;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deleted = false")
    Page<Comment> findByPostIdWithDeleted(Long postId, PageRequest pageRequest);

    @Query(value = "SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deleted = false " +
            "AND c.member NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND c NOT IN (SELECT ch.comment FROM CommentHide ch WHERE ch.member=:member)")
    Page<Comment> findByPostIdWithDeletedWithoutHide(Member member, Long postId, PageRequest pageRequest);

    @Query(value = "SELECT c FROM Comment c WHERE c.deleted = false")
    Optional<Comment> findByCommentIdWithDeleted(Long postId);

    @Query(value = "SELECT c.post FROM Comment c WHERE c.deleted = false " +
            "AND c.post.member NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND c.post.nft.member NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND c.post.nft NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member) " +
            "AND c.post NOT IN (SELECT ph.post FROM PostHide ph WHERE ph.member=:member) " +
            "AND c.createdAt >:date GROUP BY (c.post) ORDER BY COUNT(c.commentId) DESC")
    Page<Post> findPostsByCommentCount(Member member, LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT c.post FROM Comment c WHERE c.deleted = false " +
            "AND c.createdAt >:date GROUP BY (c.post) ORDER BY COUNT(c.commentId) DESC")
    Page<Post> findPostsByCommentCountWithoutHide(LocalDateTime date, PageRequest pageRequest);

    Integer countByPostAndDeletedFalse(Post post);
}
