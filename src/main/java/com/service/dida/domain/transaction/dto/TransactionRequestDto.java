package com.service.dida.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionSetDto {
        private String payTransaction;
        private String payBackTransaction;
        private String feeTransaction;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MintingTransactionDto {
        private Long buyerId;
        private Long nftId;
        private TransactionSetDto transactionSetDto;
    }
}
