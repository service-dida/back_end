package com.service.dida.domain.like.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetLikeUseCase {
    boolean checkIsLiked(Member member, Nft nft);

    PageResponseDto<List<NftResponseDto.SnsNft>> getMyLikeNftList(Member member, PageRequestDto pageRequestDto);
}
