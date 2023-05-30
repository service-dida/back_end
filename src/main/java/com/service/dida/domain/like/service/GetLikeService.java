package com.service.dida.domain.like.service;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLikeService implements GetLikeUseCase {

    private final LikeRepository likeRepository;

    /**
     * 좋아요를 누른 상태라면 true 를, 누르지 않은 상태라면 false 리턴
     * Member, Nft 검증 기능 포함 X
     */
    public boolean checkIsLiked(Member member, Nft nft) {
        Like like = likeRepository.findByMemberAndNft(member, nft).orElse(null);
        if (like == null) {
            return false;
        } else {
            return like.isStatus();
        }
    }
}
