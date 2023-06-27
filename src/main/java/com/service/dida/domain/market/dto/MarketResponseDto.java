package com.service.dida.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MarketResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetHotItem {
        private Long nftId;         // NFT ID
        private String nftImgUrl;   // NFT 이미지 주소
        private String nftName;     // NFT 이름
        private String price;       // NFT 가격
        private String likeCount;   // 좋아요 수
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetHotSeller {
        private Long memberId;              // member ID
        private String memberName;          // member
        private String profileUrl;          // member 프로필 이미지 주소
        private String nftImgUrl;           // 가장 최근에 발행한 NFT 이미지 주소
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetRecentNft {
        private Long nftId;         // NFT ID
        private String nftName;     // NFT 이름
        private String memberName;  // member 이름
        private String nftImgUrl;   // NFT 이미지 주소
        private String price;       // NFT 가격
        private boolean liked;      // 좋아요 여부
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetHotUser {
        private Long memberId;              // member ID
        private String memberName;          // member 이름
        private String profileUrl;          // member 프로필 이미지 주소
        private Long nftCount;              // 작품 수
        private boolean followed;           // 팔로우 여부
        private boolean isMe;               // 본인 여부
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMainPageWithoutSoldOut {
        List<GetHotItem> getHotItems;
        List<GetHotSeller> getHotSellers;
        List<GetRecentNft> getRecentNfts;
        List<GetHotUser> getHotUsers;
    }
}
