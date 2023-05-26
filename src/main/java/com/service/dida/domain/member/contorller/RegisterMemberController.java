package com.service.dida.domain.member.contorller;

import com.service.dida.domain.member.dto.MemberRequestDto;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.usecase.RegisterMemberUseCase;
import com.service.dida.global.config.security.oauth.helper.SocialLoginType;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterMemberController {

    private final RegisterMemberUseCase registerMemberUseCase;

    /**
     * 소셜 로그인
     */
    @PostMapping("/{socialLoginType}/login")
    public ResponseEntity<MemberResponseDto.TokenInfo> socialLogin(
        @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
        @Valid @RequestBody MemberRequestDto.SocialLoginToken socialLoginToken) throws IOException {
        return new ResponseEntity<>(
            registerMemberUseCase.socialLogin(socialLoginType, socialLoginToken), HttpStatus.OK);
    }

    /**
     * 소셜 회원가입
     */
    @PostMapping("/member")
    public ResponseEntity<MemberResponseDto.TokenInfo> register(
        @Valid @RequestBody MemberRequestDto.RegisterMember registerMember
    ) {
        return new ResponseEntity<>(registerMemberUseCase.registerMember(registerMember),
            HttpStatus.OK);
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto.CheckNickname> checkNickname(
        @Valid @RequestBody MemberRequestDto.CheckNickname checkNickname) {
        return new ResponseEntity<>(registerMemberUseCase.checkNickname(checkNickname),
            HttpStatus.OK);
    }

}
