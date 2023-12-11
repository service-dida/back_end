package com.service.dida.domain.history.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterHistoryUseCase {
    void registerHistory(Long nftId, Member member, double price);
}
