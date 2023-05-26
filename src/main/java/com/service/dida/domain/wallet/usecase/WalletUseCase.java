package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.dto.WalletResponseDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface WalletUseCase {
    void registerWallet(Long memberId, WalletRequestDto walletRequestDto)
        throws IOException, ParseException, InterruptedException;

    WalletResponseDto isExistWallet(Long memberId);
}
