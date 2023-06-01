package com.service.dida.domain.wallet.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WalletRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckPwd {
        @Pattern(regexp = "[0-9]{6}", message = "비밀번호는 숫자 6자리를 입력하여야 합니다.")
        private String payPwd;
        private String checkPwd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeCoin {
        private String payPwd;
        @Positive(message = "교환하고자 하는 코인은 양수여야 합니다.")
        private double coin;
    }


}
