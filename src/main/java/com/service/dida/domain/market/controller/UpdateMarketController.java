package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.dto.MarketRequestDto.UpdateMarket;
import com.service.dida.domain.market.usecase.UpdateMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMarketController {

    private final UpdateMarketUseCase updateMarketUseCase;

    /**
     * delete market Api
     */
    @DeleteMapping("/member/market")
    public ResponseEntity<Integer> deleteMarket(@CurrentMember Member member,
        @RequestBody UpdateMarket updateMarket)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        updateMarketUseCase.deleteMarket(member, updateMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * purchase NFT in Market Api
     */
    @PostMapping("/member/market/nft")
    public ResponseEntity<Integer> purchaseNftInMarket(@CurrentMember Member member,
        @RequestBody UpdateMarket updateMarket)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        updateMarketUseCase.purchaseNftInMarket(member, updateMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
