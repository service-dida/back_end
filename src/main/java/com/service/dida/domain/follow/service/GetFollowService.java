package com.service.dida.domain.follow.service;

import com.service.dida.domain.follow.Follow;
import com.service.dida.domain.follow.dto.FollowResponseDto.FollowList;
import com.service.dida.domain.follow.repository.FollowRepository;
import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowService implements GetFollowUseCase {

    private final FollowRepository followRepository;

    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
            , Sort.by(Sort.Direction.DESC, "updatedAt"));
    }

    @Override
    public boolean checkIsFollowed(Member member, Member owner) {
        Follow follow = followRepository.findByMemberWithOwner(member, owner).orElse(null);
        if (follow == null) {
            return false;
        } else {
            return follow.isStatus();
        }
    }

    @Override
    public PageResponseDto<List<FollowList>> getFollowerList(Member member,
        PageRequestDto pageRequestDto) {
        List<FollowList> followLists = new ArrayList<>();
        Page<Follow> follows = followRepository.findAllFollowerByMember(member,
            pageReq(pageRequestDto));
        follows.forEach(f -> followLists.add(f.getFollowingMember().setFollowList()));
        return new PageResponseDto<>(follows.getNumber(), follows.getSize(), follows.hasNext(),
            followLists);
    }
}
