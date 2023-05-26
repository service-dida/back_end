package com.service.dida.domain.wallet.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequestDto {
    @Pattern(regexp = "[0-9]{6}", message = "비밀번호는 숫자 6자리를 입력하여야 합니다.")
    private String payPwd;
    private String checkPwd;
}
