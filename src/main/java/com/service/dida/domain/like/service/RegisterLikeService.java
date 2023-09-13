package com.service.dida.domain.like.service;


import com.service.dida.domain.alarm.usecase.RegisterAlarmUseCase;
import com.service.dida.domain.like.Like;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.RegisterLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterLikeService implements RegisterLikeUseCase {
    private final LikeRepository likeRepository;
    private final NftRepository nftRepository;
    private final RegisterAlarmUseCase registerAlarmUseCase;

    public void save(Like like) {
        likeRepository.save(like);
    }

    public void createLike(Member member, Nft nft) {
        save(Like.builder()
                .member(member)
                .nft(nft)
                .status(true)
                .build());
    }

    @Override
    public boolean pushLike(Member member, Long nftId) throws IOException {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));

        Like like = likeRepository.findByMemberAndNft(member, nft).orElse(null);
        if (like == null) {
            createLike(member, nft);
            registerAlarmUseCase.registerLikeAlarm(nft.getMember(), nft.getNftId());
            return true;
        } else {
            return like.changeStatus();
        }
    }

}
