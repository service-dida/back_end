package com.service.dida.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FollowResponseDto {

    @Getter
    @AllArgsConstructor
    public static class FollowList {
        private Long memberId;
        private String nickname;
        private String profileUrl;
        private int nftCnt;
    }
}
