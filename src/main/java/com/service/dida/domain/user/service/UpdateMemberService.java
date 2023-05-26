package com.service.dida.domain.user.service;

import com.service.dida.domain.user.entity.Member;
import com.service.dida.domain.user.dto.MemberResponseDto.TokenInfo;
import com.service.dida.domain.user.repository.MemberRepository;
import com.service.dida.domain.user.usecase.UpdateMemberUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public TokenInfo refreshAccessToken(Authentication authentication) {
        Member member = memberRepository.findByMemberId((Long) authentication.getPrincipal()).orElse(null);
        if (member == null || member.isDeleted()) {
            throw new BaseException(MemberErrorCode.EMPTY_MEMBER);
        }
        return TokenInfo.builder()
            .accessToken(jwtTokenProvider.generateAccessToken(member.getMemberId()))
            .refreshToken(member.getRefreshToken())
            .build();
    }
}
