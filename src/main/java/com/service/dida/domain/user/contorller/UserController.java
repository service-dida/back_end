package com.service.dida.domain.user.contorller;

import com.service.dida.domain.user.dto.UserRequestDto;
import com.service.dida.domain.user.dto.UserResponseDto;
import com.service.dida.domain.user.usecase.RegisterUserUseCase;
import com.service.dida.global.config.security.oauth.helper.SocialLoginType;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    /**
     * 소셜 로그인
     */
    @PostMapping("/{socialLoginType}/login")
    public ResponseEntity<UserResponseDto.TokenInfo> socialLogin(
        @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
        @Valid @RequestBody UserRequestDto.SocialLoginToken socialLoginToken) throws IOException {
        return new ResponseEntity<>(
            registerUserUseCase.socialLogin(socialLoginType, socialLoginToken), HttpStatus.OK);
    }

    /**
     * 소셜 회원가입
     */
    @PostMapping("/user")
    public ResponseEntity<UserResponseDto.TokenInfo> register(
        @Valid @RequestBody UserRequestDto.RegisterMember registerMember
    ) {
        return new ResponseEntity<>(registerUserUseCase.registerMember(registerMember),
            HttpStatus.OK);
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/nickname")
    public ResponseEntity<UserResponseDto.CheckNickname> checkNickname(
        @Valid @RequestBody UserRequestDto.CheckNickname checkNickname) {
        return new ResponseEntity<>(registerUserUseCase.checkNickname(checkNickname),
            HttpStatus.OK);
    }
}
