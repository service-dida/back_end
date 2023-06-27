package com.service.dida.domain.market.service;

import com.service.dida.domain.market.dto.MarketResponseDto.GetHotItem;
import com.service.dida.domain.market.dto.MarketResponseDto.GetHotSeller;
import com.service.dida.domain.market.dto.MarketResponseDto.GetRecentNft;
import com.service.dida.domain.market.dto.MarketResponseDto.GetHotUser;
import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;

import com.service.dida.domain.like.repository.LikeRepository;
import com.service.dida.domain.market.repository.MarketRepository;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.util.usecase.UtilUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMarketService implements GetMarketUseCase {

    private final MemberRepository memberRepository;
    private final MarketRepository marketRepository;
    private final LikeRepository likeRepository;
    private final UtilUseCase utilUseCase;

    public GetHotItem makeHotItemForm(Nft nft) {
        String like = "";
        long likeCount = likeRepository.getLikeCountsByNftId(nft).orElse(0L);
        if (likeCount >= 1000) {
            like = likeCount / 1000 + "K";
        }

        return GetHotItem.builder()
            .nftId(nft.getNftId())
            .nftImgUrl(nft.getImgUrl())
            .nftName(nft.getTitle())
            .price(nft.getPrice())
            .likeCount(like)
            .build();
    }

    public List<GetHotItem> getHotItems(Member member) {
        List<GetHotItem> hotItems = new ArrayList<>();
        List<Nft> nfts = likeRepository.getHotItems(member).orElse(null);
        if (nfts != null) {
            for (Nft nft : nfts) {
                hotItems.add(makeHotItemForm(nft));
                if (hotItems.size() == 6) {
                    break;
                }
            }
        }
        return hotItems;
    }


    @Override
    public GetMainPageWithoutSoldOut getMainPage(Member member) {
        List<GetHotItem> hotItems = getHotItems(member);
        //List<GetHotSeller> hotSellers = new ArrayList<>();
        //List<GetRecentNft> recentNfts = new ArrayList<>();
        //List<GetHotUser> hotUsers = new ArrayList<>();
        return new GetMainPageWithoutSoldOut();//hotItems, hotSellers, recentNfts, hotUsers);
    }

}
