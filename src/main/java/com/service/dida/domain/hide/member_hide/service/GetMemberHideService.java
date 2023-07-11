package com.service.dida.domain.hide.member_hide.service;

import com.service.dida.domain.hide.member_hide.MemberHide;
import com.service.dida.domain.hide.member_hide.dto.MemberHideResponseDto.GetMemberHide;
import com.service.dida.domain.hide.member_hide.repository.MemberHideRepository;
import com.service.dida.domain.hide.member_hide.usecase.GetMemberHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMemberHideService implements GetMemberHideUseCase {

    private final MemberHideRepository memberHideRepository;
    private final NftRepository nftRepository;

    /**
     * Member 숨김 목록 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public GetMemberHide makeGetMemberHideForm(Member hideMember) {
        return new GetMemberHide(hideMember.getMemberId(),
                hideMember.getNickname(), hideMember.getProfileUrl(),
                nftRepository.countByMemberAndDeleted(hideMember, false));
    }

    @Override
    public PageResponseDto<List<GetMemberHide>> getMemberHideList(Member member, PageRequestDto pageRequestDto) {
        Page<MemberHide> hides = memberHideRepository.findByMember(member, pageReq(pageRequestDto));
        List<GetMemberHide> res = new ArrayList<>();

        hides.forEach(h -> res.add(makeGetMemberHideForm(h.getHideMember())));

        return new PageResponseDto<>(
                hides.getNumber(), hides.getSize(), hides.hasNext(), res);
    }
}
