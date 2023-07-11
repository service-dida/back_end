package com.service.dida.domain.hide.member_hide.usecase;

import com.service.dida.domain.hide.member_hide.dto.MemberHideResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;

import java.util.List;

public interface GetMemberHideUseCase {
    PageResponseDto<List<MemberHideResponseDto.GetMemberHide>> getMemberHideList(Member member, PageRequestDto pageRequestDto);
}
