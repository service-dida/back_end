package com.service.dida.domain.nft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NftResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class NftInfo {
        private Long nftId;
        private String nftName;
        private String nftImgUrl;
        private String price;
    }
}
