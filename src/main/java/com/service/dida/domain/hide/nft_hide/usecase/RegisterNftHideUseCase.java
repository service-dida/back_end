package com.service.dida.domain.hide.nft_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterNftHideUseCase {
    void hideNft(Member member, Long nftId);
}
