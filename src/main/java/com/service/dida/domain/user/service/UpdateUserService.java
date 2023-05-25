package com.service.dida.domain.user.service;

import com.service.dida.domain.user.entity.User;
import com.service.dida.domain.user.dto.UserResponseDto.TokenInfo;
import com.service.dida.domain.user.repository.UserRepository;
import com.service.dida.domain.user.usecase.UpdateUserUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
import com.service.dida.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public TokenInfo refreshAccessToken(Authentication authentication) {
        User user = userRepository.findByUserId((Long) authentication.getPrincipal()).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new BaseException(UserErrorCode.EMPTY_MEMBER);
        }
        return TokenInfo.builder()
            .accessToken(jwtTokenProvider.generateAccessToken(user.getUserId()))
            .refreshToken(user.getRefreshToken())
            .build();
    }
}
