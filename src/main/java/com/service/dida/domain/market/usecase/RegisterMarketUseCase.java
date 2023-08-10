package com.service.dida.domain.market.usecase;

import com.service.dida.domain.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.service.dida.domain.member.entity.Member;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface RegisterMarketUseCase {

    void registerMarket(Member member, RegisterNftToMarket registerNft)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
