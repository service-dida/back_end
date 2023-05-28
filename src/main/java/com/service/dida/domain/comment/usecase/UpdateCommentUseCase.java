package com.service.dida.domain.comment.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateCommentUseCase {
    void deleteComment(Member member, Long commentId);
}
