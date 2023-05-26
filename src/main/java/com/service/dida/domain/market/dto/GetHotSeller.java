package com.service.dida.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetHotSeller {
    private Long memberId;          // member ID
    private String memberName;        // member 이름
    private String memberProfileUrl;  // member 프로필 이미지 주소
    private String nftImgUrl;       // NFT 이미지 주소
}
