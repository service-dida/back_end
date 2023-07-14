package com.service.dida.domain.nft.dto;

import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.nft.Nft;
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

    @Getter
    @AllArgsConstructor
    public static class SnsNft {
        private Long nftId;
        private String nftName;
        private String nftImgUrl;
        private MemberInfo memberInfo;

        public SnsNft(Nft nft) {
            this.nftId = nft.getNftId();
            this.nftName = nft.getTitle();
            this.nftImgUrl = nft.getImgUrl();
            this.memberInfo = new MemberInfo(
                    nft.getMember().getMemberId(),
                    nft.getMember().getNickname(),
                    nft.getMember().getProfileUrl());
        }
    }
}
