package com.service.dida.domain.hide.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class NftHideResponseDto {
    @Data
    @AllArgsConstructor
    @Builder
    public static class GetNftHide {
        private Long nftId;
        private String nftName;
        private String nftImgUrl;
        private String memberName;
        private String price;
    }

}
