package com.service.dida.domain.hide.post_hide.repository;

import com.service.dida.domain.hide.post_hide.PostHide;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHideRepository extends JpaRepository<PostHide, Long> {

    Optional<PostHide> findByMemberAndPost(Member member, Post post);
}
