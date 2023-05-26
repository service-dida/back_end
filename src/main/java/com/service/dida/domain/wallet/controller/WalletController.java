package com.service.dida.domain.wallet.controller;

import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.usecase.RegisterWalletUseCase;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final RegisterWalletUseCase registerWalletUseCase;

    @PostMapping("/visitor/wallet")
    public ResponseEntity<Integer> registerWallet(
        @Valid @RequestBody WalletRequestDto walletRequestDto)
        throws IOException, ParseException, InterruptedException {
        registerWalletUseCase.registerWallet(
            (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
            walletRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
