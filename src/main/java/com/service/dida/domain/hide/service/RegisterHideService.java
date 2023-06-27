package com.service.dida.domain.hide.service;

import com.service.dida.domain.hide.Hide;
import com.service.dida.domain.hide.repository.HideRepository;
import com.service.dida.domain.hide.usecase.RegisterHideUseCase;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.UpdateLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.HideErrorCode;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterHideService implements RegisterHideUseCase {
    private final HideRepository hideRepository;
    private final NftRepository nftRepository;
    private final UpdateLikeUseCase updateLikeUseCase;

    @Transactional
    public void save(Hide hide) {
        hideRepository.save(hide);
    }

    public void createHide(Member member, Nft nft) {
        save(Hide.builder()
                .member(member)
                .nft(nft)
                .build());
    }

    @Override
    @Transactional
    public void hideCard(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        if (hideRepository.findByMemberAndNft(member, nft).isEmpty()) {
            createHide(member, nft);
            updateLikeUseCase.checkAndDeleteLike(member, nft);
        } else {
            throw new BaseException(HideErrorCode.ALREADY_HIDE);
        }
    }

}
