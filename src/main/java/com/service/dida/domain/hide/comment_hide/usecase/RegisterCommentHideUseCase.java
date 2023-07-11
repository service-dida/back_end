package com.service.dida.domain.hide.comment_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterCommentHideUseCase {

    void hideComment(Member member, Long commentId);
}
