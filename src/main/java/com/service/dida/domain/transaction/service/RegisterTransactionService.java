package com.service.dida.domain.transaction.service;

import static com.service.dida.global.config.constants.ServerConstants.MINTING_FEE;

import com.service.dida.domain.transaction.Transaction;
import com.service.dida.domain.transaction.TransactionType;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SendKlayOutsideTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.SwapTransactionDto;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.domain.transaction.usecase.RegisterTransactionUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterTransactionService implements RegisterTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveMintingTransaction(MintingTransactionDto mintingTransactionDto) {
        Transaction transaction = Transaction.builder()
            .type(TransactionType.MINTING)
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
    public void saveSwapTransaction(TransactionType type, SwapTransactionDto swapTransactionDto) {
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

    @Override
    public void saveSendKlayOutsideTransaction(
        SendKlayOutsideTransactionDto sendKlayOutsideTransactionDto) {
        Transaction transaction = Transaction.builder()
            .type(TransactionType.SEND_OUT_KLAY)
            .buyerId(sendKlayOutsideTransactionDto.getSenderId())
            .payAmount(sendKlayOutsideTransactionDto.getCoin())
            .build();
        transaction.setTransactionSet(sendKlayOutsideTransactionDto.getTransactionSetDto());
        save(transaction);
    }

    @Override
    public void saveSendNftOutsideTransaction(MintingTransactionDto mintingTransactionDto) {
        Transaction transaction = Transaction.builder()
            .buyerId(mintingTransactionDto.getBuyerId())
            .nftId(mintingTransactionDto.getNftId())
            .build();
        transaction.setTransactionSet(mintingTransactionDto.getTransactionSetDto());
        save(transaction);
    }


}
