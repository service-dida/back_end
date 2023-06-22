package com.service.dida.domain.nft.service;

import static com.service.dida.global.config.exception.errorCode.NftErrorCode.EMPTY_NFT;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;
import com.service.dida.domain.nft.dto.NftResponseDto.NftInfo;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.GetNftUseCase;
import com.service.dida.global.config.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNftService implements GetNftUseCase {
    private final NftRepository nftRepository;
    private final GetLikeUseCase getLikeUseCase;
    private final GetFollowUseCase getFollowUseCase;

    @Override
    public NftDetailInfo getNftDetail(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
            .orElseThrow(() -> new BaseException(EMPTY_NFT));
        Member owner = nft.getMember();
        boolean followed = member != null && getFollowUseCase.checkIsFollowed(member, owner);
        // boolean liked = member == null ? false : getLikeUseCase.checkIsLiked(member, nft) 와 같음
        boolean liked = member != null && getLikeUseCase.checkIsLiked(member, nft);
        return new NftDetailInfo(
            new NftInfo(nft.getNftId(), nft.getTitle(), nft.getImgUrl(), nft.getPrice()),
            nft.getDescription(),
            new MemberInfo(owner.getMemberId(), owner.getNickname(), owner.getProfileUrl()),
            nft.getId(), nft.getContracts(), followed, liked);
    }
}