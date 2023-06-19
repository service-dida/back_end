package com.service.dida.domain.follow.service;

import com.service.dida.domain.follow.Follow;
import com.service.dida.domain.follow.repository.FollowRepository;
import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowService implements GetFollowUseCase {

    private final FollowRepository followRepository;

    @Override
    public boolean checkIsFollowed(Member member, Member owner) {
        Follow follow = followRepository.findByMemberWithOwner(member, owner).orElse(null);
        if (follow == null) {
            return false;
        } else {
            return follow.isStatus();
        }
    }
}
