package com.service.dida.domain.transaction.controller;

import com.service.dida.domain.member.entity.Member;
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
    public ResponseEntity<PageResponseDto<List<SwapHistory>>> getSwapHistoryList(@CurrentMember
        Member member, @RequestBody PageRequestDto pageRequestDto) {
        return new ResponseEntity<>(
            getTransactionUseCase.getSwapHistoryList(member, pageRequestDto), HttpStatus.OK);
    }
}
