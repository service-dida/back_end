package com.service.dida.domain.hide.usecase;

import com.service.dida.domain.hide.dto.NftHideResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetNftHideUseCase {
    PageResponseDto<List<NftHideResponseDto.GetNftHide>> getNftHideList(Member member, PageRequestDto pageRequestDto);
    boolean checkIsNftHided(Member member, Nft nft);

}
