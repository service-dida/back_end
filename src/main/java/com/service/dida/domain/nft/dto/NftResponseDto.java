package com.service.dida.domain.nft.dto;

import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class NftDetailInfo {
        private NftInfo nftInfo;
        private String description;
        private MemberInfo memberInfo;
        private String tokenId;
        private String contractAddress;
        private boolean followed;
        private boolean liked;
    }

    @Getter
    @AllArgsConstructor
    public static class ProfileNft {
        private NftInfo nftInfo;
        private boolean liked;
    }
}
