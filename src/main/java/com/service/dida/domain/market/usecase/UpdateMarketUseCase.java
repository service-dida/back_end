package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketRequestDto.UpdateMarket;
import com.service.dida.domain.member.entity.Member;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface UpdateMarketUseCase {

    void deleteMarket(Member member, UpdateMarket updateMarket);

    void purchaseNftInMarket(Member buyer, UpdateMarket updateMarket)
        throws IOException, ParseException, InterruptedException;
}
