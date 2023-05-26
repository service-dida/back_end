package com.service.dida.domain.member.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.dto.MemberResponseDto.TokenInfo;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.member.usecase.UpdateMemberUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
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
            throw new BaseException(UserErrorCode.EMPTY_MEMBER);
        }
        return TokenInfo.builder()
            .accessToken(jwtTokenProvider.generateAccessToken(member.getMemberId()))
            .refreshToken(member.getRefreshToken())
            .build();
    }
}
