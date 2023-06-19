package com.service.dida.domain.follow.usecase;

import com.service.dida.domain.member.entity.Member;

public interface GetFollowUseCase {

    boolean checkIsFollowed(Member member, Member owner);
}
