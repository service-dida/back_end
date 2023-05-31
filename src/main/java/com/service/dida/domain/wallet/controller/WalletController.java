package com.service.dida.domain.wallet.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
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

    @PostMapping("/visitor/wallet")
    public ResponseEntity<Integer> registerWallet(
        @Valid @RequestBody WalletRequestDto walletRequestDto, @CurrentMember Member member)
        throws IOException, ParseException, InterruptedException {
        walletUseCase.registerWallet(member, walletRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    @GetMapping("/common/wallet")
    public ResponseEntity<WalletResponseDto> isExistWallet(@CurrentMember Member member) {
        return new ResponseEntity<>(walletUseCase.isExistWallet(member.getMemberId()),
            HttpStatus.OK);
    }
}
