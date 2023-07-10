package com.service.dida.domain.follow.service;

import com.service.dida.domain.follow.repository.FollowRepository;
import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.follow.usecase.UpdateFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateFollowService implements UpdateFollowUseCase {

    private final GetFollowUseCase getFollowUseCase;
    private final FollowRepository followRepository;

    @Override
    public void checkAndDeleteFollow(Member member, Member owner) {
        if (getFollowUseCase.checkIsFollowed(member, owner)) {
            followRepository.deleteByFollowingMemberAndFollowerMember(member, owner);
        }
    }
}
