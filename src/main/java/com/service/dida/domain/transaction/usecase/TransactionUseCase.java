package com.service.dida.domain.transaction.usecase;

import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;

public interface TransactionUseCase {
    void saveMintingTransaction(MintingTransactionDto mintingTransactionDto);

    void saveSwapKlayToDidaTransaction(SwapTransactionDto swapTransactionDto);
}
