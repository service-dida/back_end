package com.service.dida.domain.hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterHideUseCase {
    void hideCard(Member member, Long nftId);
}
