package com.service.dida.domain.hide.member_hide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberHideResponseDto {

    @Getter
    @AllArgsConstructor
    public static class GetMemberHide {
        private Long memberId;
        private String nickname;
        private String profileUrl;
        private int nftCnt;
    }
}
