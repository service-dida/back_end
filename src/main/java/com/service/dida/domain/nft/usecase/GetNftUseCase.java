package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;
import com.service.dida.domain.nft.dto.NftResponseDto.ProfileNft;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.List;

public interface GetNftUseCase {

    NftDetailInfo getNftDetail(Member member, Long nftId);

    PageResponseDto<List<ProfileNft>> getProfileNftList(Member member, Long memberId,
        PageRequestDto pageRequestDto, String sort);

    PageResponseDto<List<NftResponseDto.SnsNft>> getMyOwnNftList(Member member,
        PageRequestDto pageRequestDto);
}
