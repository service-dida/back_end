package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletResponseDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface WalletUseCase {
    void registerWallet(Member member, CheckPwd checkPwd)
        throws IOException, ParseException, InterruptedException;

    WalletResponseDto isExistWallet(Long memberId);

    void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException;
}
