package com.service.dida.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WalletExists {
        private boolean existed;
    }

    @Getter
    @AllArgsConstructor
    public static class MemberDetailInfo{
        private MemberInfo memberInfo;
        private String description;
        private int nftCnt;
        private int followerCnt;
        private int followingCnt;
    }
}
