package com.service.dida.domain.hide.service;

import com.service.dida.domain.hide.NftHide;
import com.service.dida.domain.hide.dto.NftHideResponseDto.GetNftHide;
import com.service.dida.domain.hide.repository.NftHideRepository;
import com.service.dida.domain.hide.usecase.GetNftHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetNftHideService implements GetNftHideUseCase {

    private final NftHideRepository nftHideRepository;

    /**
     * NFT 숨김 목록 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public GetNftHide makeGetNftHideNftForm(Nft nft) {
        return GetNftHide.builder()
                .nftId(nft.getNftId())
                .nftName(nft.getTitle())
                .nftImgUrl(nft.getImgUrl())
                .memberName(nft.getMember().getNickname())
                .price(nft.getPrice())
                .build();
    }

    @Override
    public PageResponseDto<List<GetNftHide>> getNftHideList(Member member, PageRequestDto pageRequestDto) {
        Page<NftHide> hides = nftHideRepository.findByMember(member, pageReq(pageRequestDto));

        List<GetNftHide> res = new ArrayList<>();

        for(NftHide h : hides.getContent()) {
            res.add(makeGetNftHideNftForm(h.getNft()));
        }
        return new PageResponseDto<>(
                hides.getNumber(), hides.getSize(), hides.hasNext(), res);
    }

    /**
     * 숨긴 NFT 인지 확인하는 함수, 숨긴 NFT 라면 true 리턴
     * Nft 검증 기능 포함 X
     */
    @Override
    @PreAuthorize("hasAnyRole('VISITOR, MEMBER')")
    public boolean checkIsNftHided(Member member, Nft nft) {
        return nftHideRepository.findByMemberAndNft(member, nft).isPresent();
    }
}
