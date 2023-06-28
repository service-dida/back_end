package com.service.dida.domain.hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterNftHideUseCase {
    void hideCard(Member member, Long nftId);
}
