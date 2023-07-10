package com.service.dida.domain.hide.nft_hide.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateNftHideUseCase {
    void unhideNft(Member member, Long nftId);
}
