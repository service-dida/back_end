package com.service.dida.domain.transaction.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.Transaction;
import com.service.dida.domain.transaction.dto.TransactionResponseDto.SwapHistory;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.domain.transaction.usecase.GetTransactionUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTransactionService implements GetTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
            , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public PageResponseDto<List<SwapHistory>> getSwapHistoryList(Member member,
        PageRequestDto pageRequestDto) {
        List<SwapHistory> history = new ArrayList<>();
        Page<Transaction> transactions = transactionRepository.findAllSwapHistoryByMemberId(
            member.getMemberId(), pageReq(pageRequestDto));
        transactions.forEach(t -> history.add(
            new SwapHistory(t.getTransactionId(), t.getType(), t.getPayAmount(), t.getCreatedAt()))
        );
        return new PageResponseDto<>(transactions.getNumber(), transactions.getSize(),
            transactions.hasNext(), history);
    }
}
