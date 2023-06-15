package com.service.dida.domain.nft.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.UpdateNftUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateNftService implements UpdateNftUseCase {

    private final NftRepository nftRepository;

    @Override
    public void deleteNft(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member, nftId)
            .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        nft.changeDeleted(true);
    }
}
