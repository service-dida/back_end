package com.service.dida.domain.hide.comment_hide.repository;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.hide.comment_hide.CommentHide;
import com.service.dida.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentHideRepository extends JpaRepository<CommentHide, Long> {

    Optional<CommentHide> findByMemberAndComment(Member member, Comment comment);
}
