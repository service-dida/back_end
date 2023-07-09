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
import com.service.dida.global.util.usecase.S3UseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService implements UpdateMemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final S3UseCase s3UseCase;

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
    public void updateProfileImg(Member member, MultipartFile file) throws IOException {
        member.updateProfileImg(s3UseCase.uploadImg(member.getMemberId(), file, "profile"));
        save(member);
    }

    @Override
    @Transactional
    public void updateProfileDescription(Member member, UpdateProfile updateProfile) {
        member.updateProfileDescription(updateProfile.getDescription());
        save(member);
    }

    @Override
    public void updateProfileNickname(Member member, CheckNickname checkNickname) {
        if (memberRepository.existsByNickname(checkNickname.getNickname()).orElse(false)) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
        member.updateProfileNickname(checkNickname.getNickname());
        save(member);
    }
}
