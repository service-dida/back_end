package com.service.dida.domain.market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MarketRequestDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterNftToMarket {
        @NotBlank(message = "비밀번호가 일치하지 않습니다.")
        private String payPwd;
        @PositiveOrZero(message = "NFT 번호는 음수일 수 없습니다.")
        private Long nftId;
        @Positive(message = "가격은 양수여야 합니다.")
        private double price;
    }
}
