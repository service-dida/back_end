package com.service.dida.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetHotSeller {
    private Long userId;            // 유저 ID
    private String userName;        // 유저 이름
    private String userProfileUrl;  // 유저 프로필 이미지 주소
    private String nftImgUrl;       // NFT 이미지 주소
}
