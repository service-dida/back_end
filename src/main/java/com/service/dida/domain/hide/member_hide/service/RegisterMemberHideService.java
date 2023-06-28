package com.service.dida.domain.hide.member_hide.service;

import com.service.dida.domain.hide.member_hide.MemberHide;
import com.service.dida.domain.hide.member_hide.repository.MemberHideRepository;
import com.service.dida.domain.hide.member_hide.usecase.RegisterMemberHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.HideErrorCode;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMemberHideService implements RegisterMemberHideUseCase {

    private final MemberHideRepository hideRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberHide hide) {
        hideRepository.save(hide);
    }

    public void createMemberHide(Member member, Member hideMember) {
        save(MemberHide.builder()
                .member(member)
                .hideMember(hideMember)
                .build());
    }

    @Override
    @Transactional
    public void hideMember(Member member, Long memberId) {
        Member hideMember = memberRepository.findByMemberIdWithDeleted(memberId)
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        if (hideRepository.findByMemberAndHideMember(member, hideMember).isEmpty()) {
            createMemberHide(member, hideMember);
            // 팔로우 취소 로직
        } else {
            throw new BaseException(HideErrorCode.ALREADY_HIDE);
        }
    }
}
