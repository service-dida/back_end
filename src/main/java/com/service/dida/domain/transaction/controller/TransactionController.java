package com.service.dida.domain.transaction.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.AllTypeDealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.DealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.SwapHistory;
import com.service.dida.domain.transaction.usecase.GetTransactionUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final GetTransactionUseCase getTransactionUseCase;

    /**
     * 스왑 내역 확인 Api
     */
    @GetMapping("/member/swap")
    public ResponseEntity<PageResponseDto<List<SwapHistory>>> getSwapHistoryList(
            @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(
            getTransactionUseCase.getSwapHistoryList(member,
                    new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * 전체 거래 내역 확인 Api
     */
    @GetMapping("/member/transaction")
    public ResponseEntity<PageResponseDto<List<AllTypeDealingHistory>>> getAllDealingHistory(
        @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(
            getTransactionUseCase.getAllTypeDealingHistory(member,
                    new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * 구매 내역 확인 Api
     */
    @GetMapping("/member/transaction/purchase")
    public ResponseEntity<PageResponseDto<List<DealingHistory>>> getPurchasedDealingHistory(
        @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(
            getTransactionUseCase.getPurchaseDealingHistory(member,
                    new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * 판매 내역 확인 Api
     */
    @GetMapping("/member/transaction/sale")
    public ResponseEntity<PageResponseDto<List<DealingHistory>>> getSoldDealingHistory(
        @CurrentMember Member member, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(
            getTransactionUseCase.getSoldDealingHistory(member,
                    new PageRequestDto(page, size)), HttpStatus.OK);
    }
}
