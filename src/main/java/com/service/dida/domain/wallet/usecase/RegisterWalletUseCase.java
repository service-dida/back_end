package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.wallet.dto.WalletRequestDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface RegisterWalletUseCase {
    void registerWallet(Long memberId, WalletRequestDto walletRequestDto)
        throws IOException, ParseException, InterruptedException;
}
