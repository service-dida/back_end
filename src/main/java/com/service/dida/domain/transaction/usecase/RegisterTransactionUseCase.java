package com.service.dida.domain.transaction.usecase;

import com.service.dida.domain.transaction.TransactionType;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SendKlayOutsideTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;

public interface RegisterTransactionUseCase {
    void saveMintingTransaction(MintingTransactionDto mintingTransactionDto);

    void saveSwapTransaction(TransactionType type, SwapTransactionDto swapTransactionDto);

    void saveSendKlayOutsideTransaction(
        SendKlayOutsideTransactionDto sendKlayOutsideTransactionDto);

    void saveSendNftOutsideTransaction(MintingTransactionDto mintingTransactionDto);
}
