package com.service.dida.domain.follow.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateFollowUseCase {
    void checkAndDeleteFollow(Member member, Member owner);
}
