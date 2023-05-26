package com.service.dida.domain.user.usecase;

import com.service.dida.domain.user.dto.MemberResponseDto;
import org.springframework.security.core.Authentication;

public interface UpdateMemberUseCase {
    MemberResponseDto.TokenInfo refreshAccessToken(Authentication authentication);
}
