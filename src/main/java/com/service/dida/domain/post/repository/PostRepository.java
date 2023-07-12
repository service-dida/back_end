package com.service.dida.domain.post.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.postId = :postId and p.deleted = false")
    Optional<Post> findByPostIdWithDeleted(Long postId);
    Optional<Post> findByPostId(Long postId);

    @Query(value = "SELECT p FROM Post p WHERE p.deleted = false " +
            "AND p.member NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND p NOT IN (SELECT ph.post FROM PostHide ph WHERE ph.member=:member) " +
            "AND p.nft NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member)")
    Page<Post> findAllWithDeletedWithoutHide(Member member, PageRequest pageRequest);

    @Query(value = "SELECT p FROM Post p WHERE p.deleted = false")
    Page<Post> findAllWithDeleted(PageRequest pageRequest);

    @Query(value = "SELECT p FROM Post p WHERE p.nft.nftId = :nftId AND p.deleted = false " +
            "AND p.member NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND p NOT IN (SELECT ph.post FROM PostHide ph WHERE ph.member=:member) ")
    Page<Post> findByNftIdWithDeletedWithoutHide(Member member, Long nftId, PageRequest pageRequest);

    @Query(value = "SELECT p FROM Post p WHERE p.nft.nftId = :nftId AND p.deleted = false")
    Page<Post> findByNftIdWithDeleted(Long nftId, PageRequest pageRequest);
}
