package com.service.dida.domain.hide.usecase;

import com.service.dida.domain.hide.dto.HideResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetHideUseCase {
    public PageResponseDto<List<HideResponseDto.GetHideNft>> getHideNftList(Member member, PageRequestDto pageRequestDto);
    boolean checkIsHided(Member member, Nft nft);
}
