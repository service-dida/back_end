package com.service.dida.domain.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    public static class CheckNickname {
        private boolean isUsed;
    }
}
