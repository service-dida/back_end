package com.service.dida.domain.hide.comment_hide.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.hide.comment_hide.CommentHide;
import com.service.dida.domain.hide.comment_hide.repository.CommentHideRepository;
import com.service.dida.domain.hide.comment_hide.usecase.RegisterCommentHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.CommentErrorCode;
import com.service.dida.global.config.exception.errorCode.HideErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCommentHideService implements RegisterCommentHideUseCase {

    private final CommentHideRepository hideRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void save(CommentHide hide) {
        hideRepository.save(hide);
    }

    public void createCommentHide(Member member, Comment comment) {
        save(CommentHide.builder()
                .member(member)
                .comment(comment)
                .build());
    }

    @Override
    @Transactional
    public void hideComment(Member member, Long commentId) {
        Comment comment = commentRepository.findByCommentIdWithDeleted(commentId)
                .orElseThrow(() -> new BaseException(CommentErrorCode.EMPTY_COMMENT));
        if (hideRepository.findByMemberAndComment(member, comment).isEmpty()) {
            createCommentHide(member, comment);
        } else {
            throw new BaseException(HideErrorCode.ALREADY_HIDE);
        }
    }
}
