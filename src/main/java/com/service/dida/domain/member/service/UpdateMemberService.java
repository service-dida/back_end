package com.service.dida.domain.member.service;

import com.service.dida.domain.member.dto.MemberRequestDto.CheckNickname;
import com.service.dida.domain.member.dto.MemberRequestDto.UpdateDeviceToken;
import com.service.dida.domain.member.dto.MemberRequestDto.UpdateProfile;
import com.service.dida.domain.member.dto.MemberResponseDto.TokenInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.member.usecase.UpdateMemberUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService implements UpdateMemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public TokenInfo refreshAccessToken(Member member) {
        return TokenInfo.builder()
            .accessToken(jwtTokenProvider.generateAccessToken(member.getMemberId()))
            .refreshToken(member.getRefreshToken())
            .build();
    }

    @Override
    public void updateDeviceToken(Member member, UpdateDeviceToken updateDeviceToken) {
        member.changeDeviceToken(updateDeviceToken.getDeviceToken());
        save(member);
    }

    @Override
    public void deleteMember(Member member) {
        member.setDeleted();
        save(member);
    }

    @Override
    public void updateProfileImg(Member member, UpdateProfile updateProfile) {
        member.updateProfileImg(updateProfile.getDescriptionAndImg());
    }

    @Override
    public void updateProfileDescription(Member member, UpdateProfile updateProfile) {
        member.updateProfileDescription(updateProfile.getDescriptionAndImg());
    }

    @Override
    public void updateProfileNickname(Member member, CheckNickname checkNickname) {
        if(memberRepository.existsByNickname(checkNickname.getNickname()).orElse(false)) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
        member.updateProfileNickname(checkNickname.getNickname());
    }
}
