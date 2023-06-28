package com.service.dida.domain.hide.service;

import com.service.dida.domain.hide.NftHide;
import com.service.dida.domain.hide.repository.NftHideRepository;
import com.service.dida.domain.hide.usecase.UpdateNftHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.HideErrorCode;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNftHideService implements UpdateNftHideUseCase {

    private final NftHideRepository hideRepository;
    private final NftRepository nftRepository;

    @Override
    @Transactional
    public void unhideNft(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        NftHide hide = hideRepository.findByMemberAndNft(member, nft).orElse(null);
        if(hide == null) { //비어있다면 숨기지 않은 카드이므로 예외 처리
            throw new BaseException(HideErrorCode.NOT_HIDE);
        } else {
            hideRepository.delete(hide);
        }
    }
}
