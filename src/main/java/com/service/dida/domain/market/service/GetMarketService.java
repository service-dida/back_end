package com.service.dida.domain.market.service;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.like.usecase.GetLikeUseCase;
import com.service.dida.domain.market.dto.MarketResponseDto.*;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto.*;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.transaction.repository.TransactionRepository;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MarketErrorCode;
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

    @Override
    public GetMainPageWithoutSoldOut getMainPage(Member member) {
        List<GetHotItem> hotItems = getHotItems(member);
        List<GetHotSeller> hotSellers = getHotSellers(member);
        List<GetRecentNft> recentNfts = getRecentNfts(member);
        List<GetHotMember> hotMembers = getHotMembers(member);
        return new GetMainPageWithoutSoldOut(hotItems, hotSellers, recentNfts, hotMembers);
    }

    @Override
    public GetMainSoldOut getMainPageSoldOut(Member member, int range, int page, int limit) {
        List<NftAndMemberInfo> res = new ArrayList<>();
        Page<Nft> nfts = getSoldOutPage(member, range, page, limit);
        nfts.forEach(n -> res.add(new NftAndMemberInfo(n)));
        return new GetMainSoldOut(res);
    }

    @Override
    public PageResponseDto<List<NftAndMemberInfo>> getMoreSoldOuts(Member member, int range, PageRequestDto pageRequestDto) {
        List<NftAndMemberInfo> res = new ArrayList<>();
        Page<Nft> nfts = getSoldOutPage(member, range, pageRequestDto.getPage(), pageRequestDto.getPageSize());
        nfts.forEach(n -> res.add(new NftAndMemberInfo(n)));
        return new PageResponseDto<>(nfts.getNumber(), nfts.getSize(), nfts.hasNext(), res);
    }

    /**
     * 핫 셀러(Hot Sellers) 더보기
     * controller 에서 넘겨 받은대로 paging 처리하여 가져오기
     * limit = 해당 유저가 민팅한 nftUrl을 몇개 보여줄건지
     */
    @Override
    public PageResponseDto<List<MoreHotMember>> getMoreHotSellers(Member member, PageRequestDto pageRequestDto) {
        Page<Long> sellers;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            sellers = transactionRepository.getHotSellersWithoutHide(member,
                    LocalDateTime.now().minusDays(7), PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        } else {
            sellers = transactionRepository.getHotSellers(
                    LocalDateTime.now().minusDays(7), PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        }
        return makeMoreHotMembersListForm(member, sellers, 3);
    }

    /**
     * 활발한 활동(Hot Members) 더보기
     * controller 에서 넘겨 받은대로 paging 처리하여 가져오기
     * limit = 해당 유저가 민팅한 nftUrl을 몇개 보여줄건지
     */
    @Override
    public PageResponseDto<List<MoreHotMember>> getMoreHotMembers(Member member, PageRequestDto pageRequestDto) {
        Page<Long> members;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            members = transactionRepository.getHotMembersWithoutHide(member, LocalDateTime.now().minusDays(30),
                    PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        } else {
            members = transactionRepository.getHotMembers(LocalDateTime.now().minusDays(30),
                    PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        }
        return makeMoreHotMembersListForm(member, members, 10);
    }

    /**
     * 최신 NFT(Recent Nfts) 더보기
     * controller 에서 넘겨 받은대로 paging 처리하여 가져오기
     */
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

    private List<GetHotItem> getHotItems(Member member) {
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

    private List<GetHotSeller> getHotSellers(Member member) {
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

    private List<GetRecentNft> getRecentNfts(Member member) {
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

    private List<GetHotMember> getHotMembers(Member member) {
        List<GetHotMember> hotMembers = new ArrayList<>();
        Page<Long> members;
        if (member != null) { // 로그인 했으면 숨김 리소스 제외
            members = transactionRepository.getHotMembersWithoutHide(member, LocalDateTime.now().minusDays(30),
                    PageRequest.of(0, 3));
        } else {
            members = transactionRepository.getHotMembers(LocalDateTime.now().minusDays(30),
                    PageRequest.of(0, 3));
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

    /**
     * Nft Entity를 넘겨 받아 GetHotItem DTO 형태로 만드는 함수
     */
    private GetHotItem makeHotItemForm(Nft nft) {
        return new GetHotItem(nft.getNftId(), nft.getImgUrl(), nft.getTitle(), nft.getPrice(),
                likeCountToString(likeRepository.getLikeCountsByNftId(nft).orElse(0L)));
    }

    /**
     * Member Entity를 넘겨 받아 GetHotSeller DTO 형태로 만드는 함수
     */
    private GetHotSeller makeHotSellerForm(Member member, Member owner) {
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

    /**
     * Nft Entity를 넘겨 받아 GetRecentNft DTO 형태로 만드는 함수
     */
    private GetRecentNft makeGetRecentNftForm(Member member, Nft nft) {
        boolean liked = false;
        if (member != null) {
            liked = getLikeUseCase.checkIsLiked(member, nft);
        }
        return new GetRecentNft(nft.getNftId(), nft.getTitle(), nft.getMember().getNickname(),
                nft.getImgUrl(), nft.getPrice(), liked);
    }

    /**
     * Member Entity를 넘겨 받아 GetHotMember DTO 형태로 만드는 함수
     */
    private GetHotMember makeGetHotMemberForm(Member member, Member target) {
        boolean followed = false;
        boolean isMe = false;
        if (member != null) {
            followed = getFollowUseCase.checkIsFollowed(member, target);
            isMe = checkIsMe(member.getMemberId(), target.getMemberId());
        }
        return new GetHotMember(target.getMemberId(), target.getNickname(), target.getProfileUrl(),
                nftRepository.countByMemberWithDeleted(target).orElse(0L), followed, isMe);
    }

    /**
     * Member Entity를 넘겨 받아 MoreHoMember DTO 형태로 만드는 함수
     */
    private MoreHotMember makeMoreHotMembersForm(Member member, Member target, int limit) {
        List<String> nftImgUrls;
        if (member != null) {
            nftImgUrls = nftRepository.getRecentNftImgUrlWithoutHide(member, target,
                    PageRequest.of(0, limit)).orElse(null);
        } else {
            nftImgUrls = nftRepository.getRecentNftImgUrl(target,
                    PageRequest.of(0, limit)).orElse(null);
        }
        return new MoreHotMember(makeGetHotMemberForm(member, target), nftImgUrls);
    }

    /**
     * Page 를 넘겨 받아 MoreHotSeller List를 만드는 함수
     * PageResponseDto로 반환
     */
    private PageResponseDto<List<MoreHotMember>> makeMoreHotMembersListForm(Member member, Page<Long> hotSellers, int limit) {
        List<MoreHotMember> res = new ArrayList<>();
        hotSellers.forEach(sellerId -> res.add(makeMoreHotMembersForm(member,
                memberRepository.findByMemberIdWithDeleted(sellerId)
                        .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER)), limit)));
        return new PageResponseDto<>(
                hotSellers.getNumber(), hotSellers.getSize(), hotSellers.hasNext(), res);
    }

    /**
     * Page 를 넘겨 받아 GetRecentNft List를 만드는 함수
     * PageResponseDto로 반환
     */
    private PageResponseDto<List<GetRecentNft>> makeMoreRecentNftsListForm(Member member, Page<Nft> nfts) {
        List<GetRecentNft> res = new ArrayList<>();
        nfts.forEach(nft -> res.add(makeGetRecentNftForm(member, nft)));
        return new PageResponseDto<>(
                nfts.getNumber(), nfts.getSize(), nfts.hasNext(), res);
    }

    private Page<Nft> getSoldOutPage(Member member, int range, int page, int limit) {
        if (member != null) {
            return transactionRepository.getSoldOutWithoutHide(member, rangeToLocalDateTime(range),
                    PageRequest.of(page, limit));
        } else {
            return transactionRepository.getSoldOut(rangeToLocalDateTime(range),
                    PageRequest.of(page, limit));
        }
    }

    private boolean checkIsMe(Long memberId, Long ownerId) {
        return Objects.equals(memberId, ownerId);
    }

    private String likeCountToString(long likeCount) {
        if (likeCount >= 1000) {
            return likeCount / 1000 + "K";
        } else return Long.toString(likeCount);
    }

    private LocalDateTime rangeToLocalDateTime(int range) {
        if (range == 7 || range == 30 || range == 180 || range == 365) {
            return LocalDateTime.now().minusDays(range);
        } else {
            throw new BaseException(MarketErrorCode.INVALID_TERM);
        }
    }
}
