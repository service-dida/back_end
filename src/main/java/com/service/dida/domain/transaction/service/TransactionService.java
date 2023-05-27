package com.service.dida.domain.transaction.service;

import com.service.dida.domain.transaction.Transaction;
import com.service.dida.domain.transaction.Type;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
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
            .payAmount(1D)  // 고정 금액으로 수정 필요
            .payBackAmount(null)
            .nftId(mintingTransactionDto.getNftId())
            .build();
        transaction.setTransactionSet(mintingTransactionDto.getTransactionSetDto());
        save(transaction);
    }
}
