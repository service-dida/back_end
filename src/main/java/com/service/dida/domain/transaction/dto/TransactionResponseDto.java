package com.service.dida.domain.transaction.dto;

import com.service.dida.domain.transaction.Type;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SwapHistory{
        private Long transactionId;
        private Type type;
        private double sendAmount;
        private LocalDateTime time;
    }
}
