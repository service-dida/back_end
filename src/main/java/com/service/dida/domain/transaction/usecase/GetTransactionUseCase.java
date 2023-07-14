package com.service.dida.domain.transaction.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.AllTypeDealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.DealingHistory;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.SwapHistory;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.List;

public interface GetTransactionUseCase {
    PageResponseDto<List<SwapHistory>> getSwapHistoryList(Member member,
        PageRequestDto pageRequestDto);

    PageResponseDto<List<AllTypeDealingHistory>> getAllTypeDealingHistory(Member member,
        PageRequestDto pageRequestDto);

    PageResponseDto<List<DealingHistory>> getPurchaseDealingHistory(Member member,
        PageRequestDto pageRequestDto);

    PageResponseDto<List<DealingHistory>> getSoldDealingHistory(Member member,
        PageRequestDto pageRequestDto);

}
