package com.service.dida.domain.user.service;

import com.service.dida.domain.user.Role;
import com.service.dida.domain.user.User;
import com.service.dida.domain.user.dto.UserRequestDto.CheckNickname;
import com.service.dida.domain.user.dto.UserRequestDto.RegisterMember;
import com.service.dida.domain.user.dto.UserRequestDto.SocialLoginToken;
import com.service.dida.domain.user.dto.UserResponseDto;
import com.service.dida.domain.user.dto.UserResponseDto.TokenInfo;
import com.service.dida.domain.user.repository.UserRepository;
import com.service.dida.domain.user.usecase.RegisterUserUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.AuthErrorCode;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
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
public class RegisterUserService implements RegisterUserUseCase {

    private final KakaoLoginService kakaoLoginService;
    private final AppleLoginService appleLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User register(RegisterMember registerMember) {
        User user = User.builder()
            .email(registerMember.getEmail())
            .nickname(registerMember.getNickname())
            .refreshToken("")
            .deviceToken("")
            .role(Role.ROLE_USER)
            .reportCnt(0)
            .deleted(false)
            .build();
        save(user);
        return user;
    }

    @Override
    public TokenInfo registerMember(RegisterMember registerMember) {
        checkRegister(registerMember);
        User user = register(registerMember);
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(user.getUserId());
        user.changeRefreshToken(tokenInfo.getRefreshToken());
        return tokenInfo;
    }

    @Override
    public TokenInfo socialLogin(SocialLoginType socialLoginType,
        SocialLoginToken socialLoginToken) throws IOException {
        String idToken = socialLoginToken.getIdToken();
        if (idToken.length() == 0) {
            throw new BaseException(AuthErrorCode.EMPTY_ID_TOKEN);
        }
        String email = "";
        switch (socialLoginType) {
            case KAKAO -> email = kakaoLoginService.getEmail(idToken);
            case APPLE -> email = appleLoginService.getEmail(idToken);
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(user.getUserId());
            user.changeRefreshToken(tokenInfo.getRefreshToken());
            return tokenInfo;
        } else {
            BaseException exception = new BaseException(UserErrorCode.UN_REGISTERED_MEMBER);
            exception.setEmailMessage(email);
            throw exception;
        }
    }

    @Override
    public UserResponseDto.CheckNickname checkNickname(CheckNickname checkNickname) {
        return new UserResponseDto.CheckNickname(
            userRepository.existsByNickname(checkNickname.getNickname()).orElse(false));
    }

    public void checkRegister(RegisterMember registerMember) {
        Boolean flag = userRepository.existsByEmail(registerMember.getEmail()).orElse(false);
        if (flag) {
            throw new BaseException(UserErrorCode.DUPLICATE_MEMBER);
        }
        flag = userRepository.existsByNickname(registerMember.getNickname()).orElse(false);
        if (flag) {
            throw new BaseException(UserErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
