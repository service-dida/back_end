package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketRequestDto.UpdateMarket;
import com.service.dida.domain.member.entity.Member;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.simple.parser.ParseException;

public interface UpdateMarketUseCase {

    void deleteMarket(Member member, UpdateMarket updateMarket)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    void purchaseNftInMarket(Member buyer, UpdateMarket updateMarket)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
