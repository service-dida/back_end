package com.service.dida.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDto {

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

    @Getter
    @AllArgsConstructor
    public static class MemberInfo {
        private Long memberId;
        private String memberName;
        private String profileImgUrl;
    }
}
