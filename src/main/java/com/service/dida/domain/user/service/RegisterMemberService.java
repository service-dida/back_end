package com.service.dida.domain.user.service;

import com.service.dida.domain.user.Role;
import com.service.dida.domain.user.dto.MemberRequestDto.CheckNickname;
import com.service.dida.domain.user.dto.MemberRequestDto.RegisterMember;
import com.service.dida.domain.user.dto.MemberRequestDto.SocialLoginToken;
import com.service.dida.domain.user.dto.MemberResponseDto;
import com.service.dida.domain.user.dto.MemberResponseDto.TokenInfo;
import com.service.dida.domain.user.entity.Member;
import com.service.dida.domain.user.repository.MemberRepository;
import com.service.dida.domain.user.usecase.RegisterMemberUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.security.jwt.JwtTokenProvider;
import com.service.dida.global.config.security.oauth.helper.SocialLoginType;
import com.service.dida.global.config.security.oauth.service.AppleLoginService;
import com.service.dida.global.config.security.oauth.service.KakaoLoginService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class RegisterMemberService implements RegisterMemberUseCase {

    private final KakaoLoginService kakaoLoginService;
    private final AppleLoginService appleLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member register(RegisterMember registerMember) {
        Member member = Member.builder()
            .email(registerMember.getEmail())
            .nickname(registerMember.getNickname())
            .refreshToken("")
            .deviceToken("")
            .role(Role.ROLE_VISITOR)
            .reportCnt(0)
            .deleted(false)
            .build();
        save(member);
        return member;
    }

    @Override
    public TokenInfo registerMember(RegisterMember registerMember) {
        checkRegister(registerMember);
        Member member = register(registerMember);
        MemberResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getMemberId());
        member.changeRefreshToken(tokenInfo.getRefreshToken());
        return tokenInfo;
    }

    @Override
    public TokenInfo socialLogin(SocialLoginType socialLoginType,
        SocialLoginToken socialLoginToken) throws IOException {
        String idToken = socialLoginToken.getIdToken();
        String email = "";
        switch (socialLoginType) {
            case KAKAO -> email = kakaoLoginService.getEmail(idToken);
            case APPLE -> email = appleLoginService.getEmail(idToken);
        }

        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member != null) {
            MemberResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getMemberId());
            member.changeRefreshToken(tokenInfo.getRefreshToken());
            return tokenInfo;
        } else {
            BaseException exception = new BaseException(MemberErrorCode.UN_REGISTERED_MEMBER);
            exception.setEmailMessage(email);
            throw exception;
        }
    }

    @Override
    public MemberResponseDto.CheckNickname checkNickname(CheckNickname checkNickname) {
        return new MemberResponseDto.CheckNickname(
            memberRepository.existsByNickname(checkNickname.getNickname()).orElse(false));
    }

    public void checkRegister(RegisterMember registerMember) {
        Boolean flag = memberRepository.existsByEmail(registerMember.getEmail()).orElse(false);
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_MEMBER);
        }
        flag = memberRepository.existsByNickname(registerMember.getNickname()).orElse(false);
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
