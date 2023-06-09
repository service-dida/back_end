package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.MemberRequestDto;
import com.service.dida.domain.member.dto.MemberRequestDto.CheckNickname;
import com.service.dida.domain.member.dto.MemberRequestDto.UpdateProfile;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateMemberUseCase {
    MemberResponseDto.TokenInfo refreshAccessToken(Member member);

    void updateDeviceToken(Member member, MemberRequestDto.UpdateDeviceToken updateDeviceToken);

    void deleteMember(Member member);

    void updateProfileImg(Member member, MultipartFile file) throws IOException;

    void updateProfileDescription(Member member, UpdateProfile updateProfile);

    void updateProfileNickname(Member member, CheckNickname checkNickname);
}
