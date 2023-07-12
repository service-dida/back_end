package com.service.dida.domain.market.service;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.market.dto.MarketResponseDto.*;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetMarketService implements GetMarketUseCase {


    private final LikeRepository likeRepository;
    private final TransactionRepository transactionRepository;
    private final NftRepository nftRepository;
    private final MemberRepository memberRepository;
    private final GetLikeUseCase getLikeUseCase;
    private final GetFollowUseCase getFollowUseCase;

    /**
     * Market 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public String likeCountToString(long likeCount) {
        if (likeCount >= 1000) {
            return likeCount / 1000 + "K";
        } else return Long.toString(likeCount);
    }

    public GetHotItem makeHotItemForm(Nft nft) {
        return new GetHotItem(nft.getNftId(), nft.getImgUrl(), nft.getTitle(), nft.getPrice(),
                likeCountToString(likeRepository.getLikeCountsByNftId(nft).orElse(0L)));
    }

    public GetHotSeller makeHotSellerForm(Member member, Member owner) {
        List<String> nftImgUrls;
        String nftImgUrl = "";
        if (member != null) {
            nftImgUrls = nftRepository.getRecentNftImgUrlWithoutHide(member, owner
                    , PageRequest.of(0, 1)).orElse(null);
        } else {
            nftImgUrls = nftRepository.getRecentNftImgUrl(owner,
                    PageRequest.of(0, 1)).orElse(null);
        }
        if (nftImgUrls != null) { // getRecentNftImgUrl 을 페이징으로 바꾸며 추가한 코드
            nftImgUrl = nftImgUrls.get(0);
        }
        return new GetHotSeller(owner.getMemberId(), owner.getNickname(),
                owner.getProfileUrl(), nftImgUrl);

    }

    public GetRecentNft makeGetRecentNftForm(Member member, Nft nft) {
        boolean liked = false;
        if (member != null) {
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
            nfts = likeRepository.getHotItemsWithoutHide(member).orElse(null);
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
        Page<Long> sellers;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            sellers = transactionRepository.getHotSellersWithoutHide(member,
                    LocalDateTime.now().minusDays(7), PageRequest.of(0, 4));
        } else {
            sellers = transactionRepository.getHotSellers(
                    LocalDateTime.now().minusDays(7), PageRequest.of(0, 4));
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

    public List<GetRecentNft> getRecentNfts(Member member) {
        List<GetRecentNft> recentNfts = new ArrayList<>();
        Page<Nft> nfts;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            nfts = nftRepository.getRecentNftsWithoutHide(member, PageRequest.of(0, 3));
        } else {
            nfts = nftRepository.getRecentNfts(PageRequest.of(0, 3));
        }
        nfts.forEach(nft -> recentNfts.add(makeGetRecentNftForm(member, nft)));
        return recentNfts;
    }

    public List<GetHotMember> getHotMembers(Member member) {
        List<GetHotMember> hotMembers = new ArrayList<>();
        List<Long> members;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            members = transactionRepository.getHotMembersWithoutHide(member, LocalDateTime.now().minusDays(30)).orElse(null);
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
        List<GetRecentNft> recentNfts = getRecentNfts(member);
        List<GetHotMember> hotMembers = getHotMembers(member);
        return new GetMainPageWithoutSoldOut(hotItems, hotSellers, recentNfts, hotMembers);
    }

    public MoreHotSeller makeMoreHotSellersForm(Member member, Member seller) {
        List<String> nftImgUrls;
        if (member != null) {
            nftImgUrls = nftRepository.getRecentNftImgUrlWithoutHide(member, seller,
                    PageRequest.of(0, 3)).orElse(null);
        } else {
            nftImgUrls = nftRepository.getRecentNftImgUrl(seller,
                    PageRequest.of(0, 3)).orElse(null);
        }
        return new MoreHotSeller(makeGetHotMemberForm(member, seller), nftImgUrls);
    }

    public PageResponseDto<List<MoreHotSeller>> makeMoreHotSellersListForm(Member member, Page<Long> hotSellers) {
        List<MoreHotSeller> res = new ArrayList<>();
        hotSellers.forEach(sellerId -> res.add(makeMoreHotSellersForm(member,
                memberRepository.findByMemberIdWithDeleted(sellerId)
                        .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER)))));
        return new PageResponseDto<>(
                hotSellers.getNumber(), hotSellers.getSize(), hotSellers.hasNext(), res);
    }

    public PageResponseDto<List<GetRecentNft>> makeMoreRecentNftsListForm(Member member, Page<Nft> nfts) {
        List<GetRecentNft> res = new ArrayList<>();
        nfts.forEach(nft -> res.add(makeGetRecentNftForm(member, nft)));
        return new PageResponseDto<>(
                nfts.getNumber(), nfts.getSize(), nfts.hasNext(), res);
    }

    @Override
    public PageResponseDto<List<MoreHotSeller>> getMoreHotSellers(Member member, PageRequestDto pageRequestDto) {
        Page<Long> sellers;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            sellers = transactionRepository.getHotSellersWithoutHide(member,
                    LocalDateTime.now().minusDays(7), pageReq(pageRequestDto));
        } else {
            sellers = transactionRepository.getHotSellers(
                    LocalDateTime.now().minusDays(7), pageReq(pageRequestDto));
        }
        return makeMoreHotSellersListForm(member, sellers);
    }

    @Override
    public PageResponseDto<List<GetRecentNft>> getMoreRecentNfts(Member member, PageRequestDto pageRequestDto) {
        Page<Nft> nfts;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            nfts = nftRepository.getRecentNftsWithoutHide(member, pageReq(pageRequestDto));
        } else {
            nfts = nftRepository.getRecentNfts(pageReq(pageRequestDto));
        }
        return makeMoreRecentNftsListForm(member, nfts);
    }

}
