package com.service.dida.domain.hide.post_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterPostHideUseCase {

    void hidePost(Member member, Long postId);
}
