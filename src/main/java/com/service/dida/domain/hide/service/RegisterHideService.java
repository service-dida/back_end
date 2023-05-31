package com.service.dida.domain.hide.service;

import com.service.dida.domain.hide.Hide;
import com.service.dida.domain.hide.repository.HideRepository;
import com.service.dida.domain.hide.usecase.RegisterHideUseCase;
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
        Hide hide = hideRepository.findByMemberAndNft(member, nft).orElse(null);
        if (hide == null) {
            createHide(member, nft);
        } else {
            throw new BaseException(HideErrorCode.ALREADY_HIDE);
        }
    }

}
