package com.service.dida.domain.like.service;

import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.like.usecase.UpdateLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateLikeService implements UpdateLikeUseCase {

    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void checkAndDeleteLike(Member member, Nft nft) {
        if (likeRepository.findByMemberAndNft(member, nft).isPresent()) {
            likeRepository.deleteByMemberAndNft(member, nft);
        }
    }
}
