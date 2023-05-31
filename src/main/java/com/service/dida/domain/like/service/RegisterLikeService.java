package com.service.dida.domain.like.service;


import com.service.dida.domain.like.Like;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.RegisterLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterLikeService implements RegisterLikeUseCase {
    private final LikeRepository likeRepository;
    private final NftRepository nftRepository;

    @Transactional
    public void save(Like like) {
        likeRepository.save(like);
    }

    public void createLike(Member member, Nft nft) {
        save(Like.builder()
                .member(member)
                .nft(nft)
                .build());
    }

    @Override
    @Transactional
    public boolean pushLike(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));

        Like like = likeRepository.findByMemberAndNft(member, nft).orElse(null);
        if (like == null) {
            createLike(member, nft);
            return true;
        } else {
            return like.changeStatus();
        }
    }

}
