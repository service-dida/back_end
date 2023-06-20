package com.service.dida.domain.market.service;

import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.market.repository.MarketRepository;
import com.service.dida.domain.market.dto.GetHotItem;
import com.service.dida.domain.market.dto.GetHotSeller;
import com.service.dida.domain.market.dto.GetHotUser;
import com.service.dida.domain.market.dto.GetMainPageWithoutSoldOut;
import com.service.dida.domain.market.dto.GetRecentNft;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.util.usecase.UtilUseCase;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMarketService {

    private final MemberRepository memberRepository;
    private final MarketRepository marketRepository;
    private final LikeRepository likeRepository;
    private final UtilUseCase utilUseCase;

    public GetHotItem makeHotItemForm(Nft nft) {
        // 1. 좋아요 처리
        String like = "";
        long likeCount = likeRepository.getLikeCountsByNftId(nft).orElse(0L);
        if (likeCount >= 1000) {
            like = likeCount / 1000 + "K";
        }
        // 2. 가격 처리
        String price = "";
        if (nft.isMarketed()) {
            price = utilUseCase.doubleToString(nft.getMarket().getPrice());
        }

        return GetHotItem.builder()
            .nftId(nft.getNftId())
            .nftImgUrl(nft.getImgUrl())
            .nftName(nft.getTitle())
            .price(price)
            .likeCount(like)
            .build();
    }

    public List<GetHotItem> getHotItems(Member member) {
        List<GetHotItem> hotItems = new ArrayList<>();
        List<Nft> nfts = likeRepository.getHotItems((Pageable) PageRequest.of(0, 20)).orElse(null);
        if (nfts != null) {
            for (Nft nft : nfts) {
                if (nft.isDeleted()) {
                    continue;
                }
                hotItems.add(makeHotItemForm(nft));
                if (hotItems.size() == 6) {
                    break;
                }
            }
        }
        return hotItems;
    }


    public GetMainPageWithoutSoldOut getMainPage(Long memberId) {
        Member member = memberRepository.findByMemberIdWithDeleted((memberId))
            .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));

        List<GetHotItem> hotItems = new ArrayList<>();
        List<GetHotSeller> hotSellers = new ArrayList<>();
        List<GetRecentNft> recentNfts = new ArrayList<>();
        List<GetHotUser> hotUsers = new ArrayList<>();
        return new GetMainPageWithoutSoldOut(hotItems, hotSellers, recentNfts, hotUsers);
    }

}