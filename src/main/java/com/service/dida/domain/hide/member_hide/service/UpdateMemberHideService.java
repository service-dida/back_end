package com.service.dida.domain.hide.member_hide.service;

import com.service.dida.domain.hide.member_hide.MemberHide;
import com.service.dida.domain.hide.member_hide.repository.MemberHideRepository;
import com.service.dida.domain.hide.member_hide.usecase.UpdateMemberHideUseCase;
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
public class UpdateMemberHideService implements UpdateMemberHideUseCase {

    private final MemberRepository memberRepository;
    private final MemberHideRepository hideRepository;
    @Override
    @Transactional
    public void unhideMember(Member member, Long memberId) {
        Member hideMember = memberRepository.findByMemberIdWithDeleted(memberId)
                .orElseThrow(() -> new BaseException(MemberErrorCode.INVALID_MEMBER));
        MemberHide hide = hideRepository.findByMemberAndHideMember(member, hideMember).orElse(null);
        if(hide == null) { //비어있다면 숨기지 않은 카드이므로 예외 처리
            throw new BaseException(HideErrorCode.NOT_HIDE);
        } else {
            hideRepository.delete(hide);
        }
    }
}
