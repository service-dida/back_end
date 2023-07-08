package com.service.dida.domain.follow.service;

import com.service.dida.domain.follow.Follow;
import com.service.dida.domain.follow.repository.FollowRepository;
import com.service.dida.domain.follow.usecase.RegisterFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterFollowService implements RegisterFollowUseCase {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public void save(Follow follow) {
        followRepository.save(follow);
    }

    public void register(Member member, Member other) {
        Follow follow = Follow.builder()
            .followerMember(other)
            .followingMember(member)
            .status(true)
            .build();
        save(follow);
    }

    @Override
    public void registerFollow(Member member, Long otherId) {
        if (member.getMemberId().equals(otherId)) {
            throw new BaseException(MemberErrorCode.INVALID_MEMBER);
        }
        Member other = memberRepository.findByMemberIdWithDeleted(otherId)
            .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        Follow follow = followRepository.findByMemberWithOwner(member, other).orElse(null);
        if (follow == null) {
            register(member, other);
        } else {
            follow.changeStatus();
        }
    }
}
