package com.service.dida.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NftLikeRequestDto {
        @NotBlank(message = "NFT의 ID를 입력해주세요.")
        private Long nftId;
    }

}
