package com.service.dida.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetMainPageWithoutSoldOut {
    List<GetHotItem> getHotItems;
    List<GetHotSeller> getHotSellers;
    List<GetRecentNft> getRecentNfts;
    List<GetHotUser> getHotUsers;
}
