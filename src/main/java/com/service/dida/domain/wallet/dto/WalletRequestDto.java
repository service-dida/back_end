package com.service.dida.domain.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WalletRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckPwdForNft {
        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        private String payPwd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckPwd {
        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        private String payPwd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePwd {
        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        private String nowPwd;
        private String changePwd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeCoin {
        private String payPwd;
        @Positive(message = "교환하고자 하는 코인은 양수여야 합니다.")
        private double coin;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendKlayOutside{
        private ChangeCoin changeCoin;
        @NotBlank(message = "주소는 공백일 수 없습니다.")
        private String address;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendNftRequestDto {
        @NotBlank(message = "비밀번호가 일치하지 않습니다.")
        private String payPwd;
        @NotBlank(message = "Nft Id가 없습니다.")
        private Long nftId;
        @NotBlank(message = "주소가 없습니다.")
        private String address;
    }
}
