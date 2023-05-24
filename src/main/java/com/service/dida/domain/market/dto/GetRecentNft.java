package com.service.dida.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetRecentNft {
    private Long nftId;         // NFT ID
    private String nftName;     // NFT 이름
    private String userName;    // 유저 이름
    private String nftImgUrl;   // NFT 이미지 주소
    private String price;       // NFT 가격
    private boolean liked;      // 좋아요 여부
}
