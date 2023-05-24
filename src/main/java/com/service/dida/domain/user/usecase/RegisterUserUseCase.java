package com.service.dida.domain.user.usecase;

import com.service.dida.domain.user.dto.UserRequestDto;
import com.service.dida.domain.user.dto.UserResponseDto;
import com.service.dida.global.config.security.oauth.helper.SocialLoginType;
import java.io.IOException;

public interface RegisterUserUseCase {

    UserResponseDto.TokenInfo socialLogin(SocialLoginType socialLoginType,
        UserRequestDto.SocialLoginToken socialLoginToken) throws IOException;

    UserResponseDto.TokenInfo registerMember(UserRequestDto.RegisterMember registerMember);

    UserResponseDto.CheckNickname checkNickname(UserRequestDto.CheckNickname checkNickname);
}
