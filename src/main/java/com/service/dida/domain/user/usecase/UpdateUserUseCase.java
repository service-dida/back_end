package com.service.dida.domain.user.usecase;

import com.service.dida.domain.user.dto.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface UpdateUserUseCase {
    UserResponseDto.TokenInfo refreshAccessToken(Authentication authentication);
}
