package com.service.dida.domain.market.service;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.market.dto.MarketResponseDto.GetHotItem;
import com.service.dida.domain.market.dto.MarketResponseDto.GetHotSeller;
import com.service.dida.domain.market.dto.MarketResponseDto.GetRecentNft;
import com.service.dida.domain.market.dto.MarketResponseDto.GetHotMember;
import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;

import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMarketService implements GetMarketUseCase {


    private final LikeRepository likeRepository;
    private final TransactionRepository transactionRepository;
    private final NftRepository nftRepository;
    private final MemberRepository memberRepository;
    private final GetLikeUseCase getLikeUseCase;
    private final GetFollowUseCase getFollowUseCase;

    public String likeCountToString(long likeCount) {
        if (likeCount >= 1000) {
            return likeCount / 1000 + "K";
        } else return likeCountToString(likeCount);
    }

    public GetHotItem makeHotItemForm(Nft nft) {
        return new GetHotItem(nft.getNftId(), nft.getImgUrl(), nft.getTitle(), nft.getPrice(),
                likeCountToString(likeRepository.getLikeCountsByNftId(nft).orElse(0L)));
    }

    public GetHotSeller makeHotSellerForm(Member member, Member owner) {
        String nftImgUrl = "";
        if(member != null) {
            nftImgUrl = nftRepository.getRecentNftImgUrlMinusHide(member, owner).orElse("");
        } else {
            nftImgUrl = nftRepository.getRecentNftImgUrl(owner).orElse("");
        }
        return new GetHotSeller(owner.getMemberId(), owner.getNickname(),
                owner.getProfileUrl(), nftImgUrl);

    }

    public GetRecentNft makeGetRecentNftForm(Member member, Nft nft) {
        boolean liked = false;
        if(member != null) {
            liked = getLikeUseCase.checkIsLiked(member, nft);
        }
        return new GetRecentNft(nft.getNftId(), nft.getTitle(), nft.getMember().getNickname(),
                nft.getImgUrl(), nft.getPrice(), liked);
    }

    public GetHotMember makeGetHotMemberForm(Member member, Member hotMember) {
        boolean followed = false;
        boolean isMe = false;
        if (member != null) {
            followed = getFollowUseCase.checkIsFollowed(member, hotMember);
            isMe = checkIsMe(member.getMemberId(), hotMember.getMemberId());
        }
        return new GetHotMember(hotMember.getMemberId(), hotMember.getNickname(), hotMember.getProfileUrl(),
                nftRepository.countByMemberWithDeleted(hotMember).orElse(0L), followed, isMe);
    }

    public boolean checkIsMe(Long memberId, Long ownerId) {
        return Objects.equals(memberId, ownerId);
    }

    public List<GetHotItem> getHotItems(Member member) {
        List<GetHotItem> hotItems = new ArrayList<>();
        List<Nft> nfts;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            nfts = likeRepository.getHotItemsMinusHide(member).orElse(null);
        } else {
            nfts = likeRepository.getHotItems().orElse(null);
        }
        if (nfts != null) {
            for (Nft nft : nfts) {
                hotItems.add(makeHotItemForm(nft));
            }
        }
        return hotItems;
    }

    public List<GetHotSeller> getHotSellers(Member member) {
        List<GetHotSeller> hotSellers = new ArrayList<>();
        List<Long> sellers;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            sellers = transactionRepository.getHotSellersMinusHide(member,
                    LocalDateTime.now().minusDays(7)).orElse(null);
        } else {
            sellers = transactionRepository.getHotSellers(
                    LocalDateTime.now().minusDays(7)).orElse(null);
        }
        if (sellers != null) {
            for (Long sellerId : sellers) {
                Member seller = memberRepository.findByMemberIdWithDeleted(sellerId)
                        .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
                hotSellers.add(makeHotSellerForm(member, seller));
            }
        }
        return hotSellers;
    }

    public List<GetRecentNft> getRecentNfts(Member member, PageRequest pageRequest) {
        List<GetRecentNft> recentNfts = new ArrayList<>();
        Page<Nft> nfts;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            nfts = nftRepository.getRecentNftsMinusHide(member, pageRequest);
        } else {
            nfts = nftRepository.getRecentNfts(pageRequest);
        }
        nfts.forEach(nft -> recentNfts.add(makeGetRecentNftForm(member, nft)));
        return recentNfts;
    }

    public List<GetHotMember> getHotMembers(Member member) {
        List<GetHotMember> hotMembers = new ArrayList<>();
        List<Long> members;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            members = transactionRepository.getHotMembersMinusHide(member, LocalDateTime.now().minusDays(30)).orElse(null);
        } else {
            members = transactionRepository.getHotMembers(LocalDateTime.now().minusDays(30)).orElse(null);
        }

        if (members != null) {
            for (Long memberId : members) {
                Member hotMember = memberRepository.findByMemberIdWithDeleted(memberId)
                        .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
                hotMembers.add(makeGetHotMemberForm(member, hotMember));
            }
        }
        return hotMembers;
    }

    @Override
    public GetMainPageWithoutSoldOut getMainPage(Member member) {
        List<GetHotItem> hotItems = getHotItems(member);
        List<GetHotSeller> hotSellers = getHotSellers(member);
        List<GetRecentNft> recentNfts = getRecentNfts(member, PageRequest.of(0, 4));
        List<GetHotMember> hotMembers = getHotMembers(member);
        return new GetMainPageWithoutSoldOut(hotItems, hotSellers, recentNfts, hotMembers);
    }

}
