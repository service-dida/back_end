package com.service.dida.domain.wallet.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletResponseDto;
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
     * 지갑 보유 여부 확인하기
     */
    @GetMapping("/common/wallet")
    public ResponseEntity<WalletResponseDto> isExistWallet(@CurrentMember Member member) {
        return new ResponseEntity<>(walletUseCase.isExistWallet(member.getMemberId()),
            HttpStatus.OK);
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

}
