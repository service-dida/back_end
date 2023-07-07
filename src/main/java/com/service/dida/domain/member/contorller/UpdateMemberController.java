package com.service.dida.domain.member.contorller;

import com.service.dida.domain.member.dto.MemberRequestDto;
import com.service.dida.domain.member.dto.MemberRequestDto.UpdateProfile;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.usecase.UpdateMemberUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMemberController {

    private final UpdateMemberUseCase updateMemberUseCase;

    /**
     * 로그인 토큰 갱신
     */
    @PatchMapping("/common/refresh")
    public ResponseEntity<MemberResponseDto.TokenInfo> refreshLogin(@CurrentMember Member member) {
        return new ResponseEntity<>(updateMemberUseCase.refreshAccessToken(member), HttpStatus.OK);
    }

    /**
     * 디바이스 토큰 변경 Api
     */
    @PatchMapping("/member/device")
    public ResponseEntity<Integer> updateDeviceToken(@CurrentMember Member member,
        @Valid @RequestBody MemberRequestDto.UpdateDeviceToken updateDeviceToken) {
        updateMemberUseCase.updateDeviceToken(member, updateDeviceToken);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 계정 삭제 Api
     */
    @DeleteMapping("/member")
    public ResponseEntity<Integer> deleteMember(@CurrentMember Member member) {
        updateMemberUseCase.deleteMember(member);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 프로필 이미지 변경 Api
     */
    @PatchMapping("/common/image")
    public ResponseEntity<Integer> updateProfileImg(@CurrentMember Member member,
        @RequestBody UpdateProfile updateProfile) {
        updateMemberUseCase.updateProfileImg(member, updateProfile);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 프로필 설명 변경 Api
     */
    @PatchMapping("/common/description")
    public ResponseEntity<Integer> updateProfileDescription(@CurrentMember Member member,
        @RequestBody UpdateProfile updateProfile) {
        updateMemberUseCase.updateProfileDescription(member, updateProfile);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
