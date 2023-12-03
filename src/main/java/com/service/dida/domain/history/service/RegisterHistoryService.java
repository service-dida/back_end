package com.service.dida.domain.history.service;

import com.service.dida.domain.history.History;
import com.service.dida.domain.history.repository.HistoryRepository;
import com.service.dida.domain.history.usecase.RegisterHistoryUseCase;
import com.service.dida.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterHistoryService implements RegisterHistoryUseCase {

    private final HistoryRepository historyRepository;

    private void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public void registerHistory(Long nftId, Member member) {
        History history = History.builder()
            .createdAt(LocalDateTime.now())
            .nftId(nftId)
            .member(member)
            .build();
        save(history);
    }
}
