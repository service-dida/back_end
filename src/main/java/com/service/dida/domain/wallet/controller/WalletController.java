package com.service.dida.domain.wallet.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendKlayOutside;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendNftRequestDto;
import com.service.dida.domain.wallet.dto.WalletResponseDto.WalletDetail;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletUseCase walletUseCase;

    @GetMapping("/member/wallet")
    public ResponseEntity<WalletDetail> getWalletDetail(@CurrentMember Member member)
        throws IOException, ParseException, InterruptedException {
        return new ResponseEntity<>(walletUseCase.getWalletDetail(member), HttpStatus.OK);
    }

    /**
     * 지갑 발급하기 Api
     */
    @PostMapping("/visitor/wallet")
    public ResponseEntity<Integer> registerWallet(
        @Valid @RequestBody WalletRequestDto.CheckPwd checkPwd, @CurrentMember Member member)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.registerWallet(member, checkPwd);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 클레이를 디다로 교환하기
     */
    @PostMapping("/member/klay")
    public ResponseEntity<Integer> swapKlayToDida(@CurrentMember Member member,
        @Valid @RequestBody ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.swapKlayToDida(member, changeCoin);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 디다를 클레이로 교환하기
     */
    @PostMapping("/member/dida")
    public ResponseEntity<Integer> swapDidaToKlay(@CurrentMember Member member,
        @Valid @RequestBody ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.swapDidaToKlay(member, changeCoin);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 클레이를 외부 전송하기
     */
    @PostMapping("/member/klay/outside")
    public ResponseEntity<Integer> sendKlayOutside(@CurrentMember Member member,
        @Valid @RequestBody SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.sendKlayOutside(member, sendKlayOutside);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }


    /**
     * NFT 전송하기 Api
     */
    @PostMapping("/member/nft/outside")
    public ResponseEntity<Integer> sendNftOutside(@CurrentMember Member member,
        @RequestBody SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.sendNftOutside(member, sendNftRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
