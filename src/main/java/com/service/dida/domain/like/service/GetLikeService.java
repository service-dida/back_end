package com.service.dida.domain.like.service;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto.SnsNft;
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
public class GetLikeService implements GetLikeUseCase {

    private final LikeRepository likeRepository;

    /**
     * 좋아요를 누른 상태라면 true 를, 누르지 않은 상태라면 false 리턴
     * Nft 검증 기능 포함 X
     */
    @Override
    @PreAuthorize("hasAnyRole('VISITOR, MEMBER')")
    public boolean checkIsLiked(Member member, Nft nft) {
        Like like = likeRepository.findByMemberAndNft(member, nft).orElse(null);
        if (like == null) {
            return false;
        } else {
            return like.isStatus();
        }
    }

    @Override
    public PageResponseDto<List<SnsNft>> getMyLikeNftList(Member member, PageRequestDto pageRequestDto) {
        List<SnsNft> res = new ArrayList<>();
        Page<Nft> nfts = likeRepository.getNftsByMemberAndStatusTrue(member,
                PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "updatedAt")));

        nfts.forEach(nft -> res.add(new SnsNft(nft)));

        return new PageResponseDto<>(nfts.getNumber(), nfts.getSize(), nfts.hasNext(), res);
    }
}
