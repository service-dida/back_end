package com.service.dida.domain.follow.usecase;

import com.service.dida.domain.follow.dto.FollowResponseDto.FollowList;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.List;

public interface GetFollowUseCase {

    boolean checkIsFollowed(Member member, Member owner);

    PageResponseDto<List<FollowList>> getFollowerList(Member member, PageRequestDto pageRequestDto);

    PageResponseDto<List<FollowList>> getFollowingList(Member member, PageRequestDto pageRequestDto);
}
