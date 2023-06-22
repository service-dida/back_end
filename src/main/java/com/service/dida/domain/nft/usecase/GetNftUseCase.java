package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;

public interface GetNftUseCase {

    NftDetailInfo getNftDetail(Member member,Long nftId);
}
