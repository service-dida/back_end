package com.service.dida.domain.user.service;

import com.service.dida.domain.user.dto.SendAuthEmailDto;
import com.service.dida.domain.user.entity.Member;
import com.service.dida.domain.user.repository.MemberRepository;
import com.service.dida.domain.user.usecase.GetMemberUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.util.mail.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMemberService implements GetMemberUseCase {

    private final MemberRepository memberRepository;
    private final MailUseCase mailUseCase;

    @Override
    public SendAuthEmailDto sendAuthMail(Authentication authentication) {
        Member member = memberRepository.findByMemberId((Long) authentication.getPrincipal()).orElse(null);
        if (member == null || member.isDeleted()) {
            throw new BaseException(MemberErrorCode.EMPTY_MEMBER);
        }
        return new SendAuthEmailDto(mailUseCase.sendAuthMail(member.getEmail()));
    }
}
