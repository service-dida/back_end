package com.service.dida.domain.transaction.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.AllTypeDealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.DealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.SwapHistory;
import com.service.dida.domain.transaction.usecase.GetTransactionUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final GetTransactionUseCase getTransactionUseCase;

    /**
     * 스왑 내역 확인 Api
     */
    @GetMapping("/member/swap")
    public ResponseEntity<PageResponseDto<List<SwapHistory>>> getSwapHistoryList(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
            getTransactionUseCase.getSwapHistoryList(member, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 전체 거래 내역 확인 Api
     */
    @GetMapping("/member/transaction")
    public ResponseEntity<PageResponseDto<List<AllTypeDealingHistory>>> getAllDealingHistory(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
            getTransactionUseCase.getAllTypeDealingHistory(member, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 구매 내역 확인 Api
     */
    @GetMapping("/member/transaction/1")
    public ResponseEntity<PageResponseDto<List<DealingHistory>>> getPurchasedDealingHistory(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
            getTransactionUseCase.getPurchaseDealingHistory(member, pageRequestDto), HttpStatus.OK);
    }

    /**
     * 판매 내역 확인 Api
     */
    @GetMapping("/member/transaction/2")
    public ResponseEntity<PageResponseDto<List<DealingHistory>>> getSoldDealingHistory(
        @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
            getTransactionUseCase.getSoldDealingHistory(member, pageRequestDto), HttpStatus.OK);
    }
}
