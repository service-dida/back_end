package com.service.dida.domain.nft.service;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto.SnsNft;
import com.service.dida.domain.nft.dto.NftResponseDto.NftDetailInfo;
import com.service.dida.domain.nft.dto.NftResponseDto.NftInfo;
import com.service.dida.domain.nft.dto.NftResponseDto.ProfileNft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.GetNftUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.service.dida.global.config.exception.errorCode.NftErrorCode.EMPTY_NFT;

@Service
@RequiredArgsConstructor
public class GetNftService implements GetNftUseCase {
    private final NftRepository nftRepository;
    private final MemberRepository memberRepository;
    private final GetLikeUseCase getLikeUseCase;
    private final GetFollowUseCase getFollowUseCase;

    public PageRequest pageReq(PageRequestDto pageRequestDto, Direction sort) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
            , Sort.by(sort, "updatedAt"));
    }

    @Override
    public NftDetailInfo getNftDetail(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeleted(nftId)
            .orElseThrow(() -> new BaseException(EMPTY_NFT));
        Member owner = nft.getMember();
        boolean isMe, followed, liked;
        Long marketId = 0L;
        if (nft.getMarket() != null) {
            marketId = nft.getMarket().getMarketId();
        }
        if (member == null) {
            isMe = false;
            followed = false;
            liked = false;
        } else {
            isMe = checkIsMe(member.getMemberId(), nft.getMember().getMemberId());
            followed = getFollowUseCase.checkIsFollowed(member, owner);
            // boolean liked = member == null ? false : getLikeUseCase.checkIsLiked(member, nft) 와 같음
            liked = getLikeUseCase.checkIsLiked(member, nft);
        }
        return new NftDetailInfo(
            new NftInfo(nft.getNftId(), nft.getTitle(), nft.getImgUrl(), nft.getPrice()),
            nft.getDescription(),
            new MemberInfo(owner.getMemberId(), owner.getNickname(), owner.getProfileUrl()),
            nft.getId(), nft.getContracts(), followed, liked, isMe, marketId);
    }

    @Override
    public PageResponseDto<List<ProfileNft>> getProfileNftList(Member member, Long memberId,
        PageRequestDto pageRequestDto, String sort) {
        checkSortingWord(sort);
        List<ProfileNft> profileNfts = new ArrayList<>();
        Page<Nft> nfts = null;
        String memberName;
        if (memberId == null) {
            if (sort.equals("updated_desc")) {
                nfts = nftRepository.findAllNftsByMember(member,
                    pageReq(pageRequestDto, Direction.DESC));
            } else if (sort.equals("updated_asc")) {
                nfts = nftRepository.findAllNftsByMember(member,
                    pageReq(pageRequestDto, Direction.ASC));
            }
            memberName = member.getNickname();
        } else {
            Member other = memberRepository.findByMemberIdWithDeleted(memberId).orElseThrow(() ->
                new BaseException(MemberErrorCode.EMPTY_MEMBER));
            if (sort.equals("updated_desc")) {
                nfts = nftRepository.findAllNftsByMember(other,
                    pageReq(pageRequestDto, Direction.DESC));
            } else if (sort.equals("updated_asc")) {
                nfts = nftRepository.findAllNftsByMember(other,
                    pageReq(pageRequestDto, Direction.ASC));
            }
            memberName = other.getNickname();
        }
        nfts.forEach(n -> profileNfts.add(new ProfileNft(
            new NftInfo(n.getNftId(), n.getTitle(), n.getImgUrl(), n.getPrice()),
            memberName, getLikeUseCase.checkIsLiked(member, n))));

        return new PageResponseDto<>(nfts.getNumber(), nfts.getSize(), nfts.hasNext(), profileNfts);
    }

    @Override
    public PageResponseDto<List<SnsNft>> getMyOwnNftList(Member member,
        PageRequestDto pageRequestDto) {
        List<SnsNft> res = new ArrayList<>();
        Page<Nft> nfts = nftRepository.findAllNftsByMember(member,
            pageReq(pageRequestDto, Direction.DESC));

        nfts.forEach(nft -> res.add(new SnsNft(nft)));

        return new PageResponseDto<>(nfts.getNumber(), nfts.getSize(), nfts.hasNext(), res);
    }

    public void checkSortingWord(String sort) {
        if (!sort.equals("updated_desc") && !sort.equals("updated_asc")) {
            throw new BaseException(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR);
        }
    }

    private boolean checkIsMe(Long memberId, Long ownerId) {
        return Objects.equals(memberId, ownerId);
    }
}
