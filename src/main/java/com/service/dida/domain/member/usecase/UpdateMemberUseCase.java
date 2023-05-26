package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.MemberResponseDto;
import org.springframework.security.core.Authentication;

public interface UpdateMemberUseCase {
    MemberResponseDto.TokenInfo refreshAccessToken(Authentication authentication);
}
