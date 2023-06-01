package com.service.dida.domain.transaction.service;

import static com.service.dida.global.config.constants.ServerConstants.MINTING_FEE;

import com.service.dida.domain.transaction.Transaction;
import com.service.dida.domain.transaction.Type;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.domain.transaction.usecase.TransactionUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService implements TransactionUseCase {

    private final TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveMintingTransaction(MintingTransactionDto mintingTransactionDto) {
        Transaction transaction = Transaction.builder()
            .type(Type.MINTING)
            .buyerId(mintingTransactionDto.getBuyerId())
            .sellerId(null)
            .payAmount(MINTING_FEE)
            .payBackAmount(null)
            .nftId(mintingTransactionDto.getNftId())
            .build();
        transaction.setTransactionSet(mintingTransactionDto.getTransactionSetDto());
        save(transaction);
    }

    @Override
    public void saveSwapTransaction(Type type, SwapTransactionDto swapTransactionDto) {
        Transaction transaction = Transaction.builder()
            .type(type)
            .buyerId(swapTransactionDto.getSwaperId())
            .payAmount(swapTransactionDto.getCoin())
            .payBackAmount(swapTransactionDto.getCoin())
            .nftId(null)
            .build();
        transaction.setTransactionSet(swapTransactionDto.getTransactionSetDto());
        save(transaction);
    }


}
