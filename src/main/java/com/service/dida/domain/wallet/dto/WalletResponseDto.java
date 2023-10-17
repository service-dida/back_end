package com.service.dida.domain.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WalletResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WalletDetail {
        private String address;
        private double klay;
        private double dida;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WrongCnt {
        private boolean matched;
        private int wrongCnt;
    }
}
