package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateNftUseCase {
    void deleteNft(Member member,Long nftId);
}
