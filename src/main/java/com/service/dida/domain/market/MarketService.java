package com.service.dida.domain.market;

import com.service.dida.global.config.exception.errorCode.UserErrorCode;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.domain.like.LikeRepository;
import com.service.dida.domain.market.dto.*;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.user.entity.User;
import com.service.dida.domain.user.repository.UserRepository;
import com.service.dida.global.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final LikeRepository likeRepository;
    private final UtilService utilService;

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
            price = utilService.doubleToString(nft.getMarkets().get(nft.getMarkets().size() - 1).getPrice());
        }

        return GetHotItem.builder()
                .nftId(nft.getNftId())
                .nftImgUrl(nft.getImgUrl())
                .userName(nft.getUser().getNickname())
                .price(price)
                .likeCount(like)
                .build();
    }

    public List<GetHotItem> getHotItems(User user) {
        List<GetHotItem> hotItems = new ArrayList<>();
        List<Nft> nfts = likeRepository.getHotItems((Pageable) PageRequest.of(0, 20)).orElse(null);
        if (nfts != null) {
            for (Nft nft : nfts) {
                if (nft.isValidated()) {
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


    public GetMainPageWithoutSoldOut getMainPage(Long userId) {
        User user = userRepository.findByUserId(userId).orElse(null);
        if (user.isValidated()) {
            throw new BaseException(UserErrorCode.EMPTY_MEMBER);
        }
        List<GetHotItem> hotItems = new ArrayList<>();
        List<GetHotSeller> hotSellers = new ArrayList<>();
        List<GetRecentNft> recentNfts = new ArrayList<>();
        List<GetHotUser> hotUsers = new ArrayList<>();
        return new GetMainPageWithoutSoldOut(hotItems, hotSellers, recentNfts, hotUsers);
    }

}
