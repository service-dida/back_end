package com.service.dida.domain.hide.member_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterMemberHideUseCase {
    void hideMember(Member member, Long memberId);
}
