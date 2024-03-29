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
        private boolean isMe;
        private Long marketId;
    }

    @Getter
    @AllArgsConstructor
    public static class ProfileNft {
        private NftInfo nftInfo;
        private String memberName;
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

    @Getter
    @AllArgsConstructor
    public static class NftAndMemberInfo{
        private NftInfo nftInfo;
        private MemberInfo memberInfo;

        public NftAndMemberInfo(Nft nft) {
            this.nftInfo = new NftInfo(
                    nft.getNftId(),
                    nft.getTitle(),
                    nft.getImgUrl(),
                    nft.getPrice());
            this.memberInfo = new MemberInfo(
                    nft.getMember().getMemberId(),
                    nft.getMember().getNickname(),
                    nft.getMember().getProfileUrl());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class NftId {
        private Long nftId;
    }
}
