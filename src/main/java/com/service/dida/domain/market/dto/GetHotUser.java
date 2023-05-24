package com.service.dida.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetHotUser {
    private Long userId;            // 유저 ID
    private String userName;        // 유저 이름
    private String userProfileUrl;  // 유저의 프로필 이미지 주소
    private Long nftCount;          // 작품 수
    private boolean followed;       // 팔로우 여부
    private boolean isMe;           // 본인 여부
}
