package com.service.dida.domain.comment.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.comment.usecase.UpdateCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.CommentErrorCode;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCommentService implements UpdateCommentUseCase {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * 나의 댓글인지 체크하는 함수
     */
    public boolean checkIsMe(Long memberId, Long ownerId) {
        memberRepository.findByMemberIdWithDeleted((memberId))
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));

        if (memberId.equals(ownerId)) {
            return true;
        } else {
            throw new BaseException(CommentErrorCode.NOT_OWN_COMMENT);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Member member, Long commentId) {
        Comment comment = commentRepository.findByCommentIdWithDeleted(commentId)
                .orElseThrow(() -> new BaseException(CommentErrorCode.EMPTY_COMMENT));

        if (checkIsMe(member.getMemberId(), comment.getMember().getMemberId())) {
            comment.setDeleted();
        }
    }
}
