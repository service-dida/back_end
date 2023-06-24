package com.service.dida.domain.transaction.dto;

import com.service.dida.domain.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SwapHistory {
        private Long transactionId;
        private TransactionType type;
        private double coin;
        private String time;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DealingHistory {
        private Long transactionId;
        private Long nftId;
        private String nftTitle;
        private double price;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AllTypeDealingHistory {
        private DealingHistory dealingHistory;
        private boolean isPurchased;
    }
}
