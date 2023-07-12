package com.service.dida.domain.hide.member_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateMemberHideUseCase {

    void unhideMember(Member member, Long memberId);
}
