package com.service.dida.domain.transaction.usecase;

import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;

public interface TransactionUseCase {
    void saveMintingTransaction(MintingTransactionDto mintingTransactionDto);
}
