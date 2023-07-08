package com.service.dida.domain.follow.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterFollowUseCase {
    void registerFollow(Member member,Long otherId);
}
