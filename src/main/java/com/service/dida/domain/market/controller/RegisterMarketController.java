package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.dto.MarketRequestDto.RegisterNftToMarket;
import com.service.dida.domain.market.usecase.RegisterMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterMarketController {

    private final RegisterMarketUseCase registerMarketUseCase;

    /**
     * 마켓에 NFT 등록하기 Api
     */
    @PostMapping("/member/market")
    public ResponseEntity<Integer> registerMarket(@CurrentMember Member member,
        @RequestBody RegisterNftToMarket registerNftToMarket)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        registerMarketUseCase.registerMarket(member, registerNftToMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
