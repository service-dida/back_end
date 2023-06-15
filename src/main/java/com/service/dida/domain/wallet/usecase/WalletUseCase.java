package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendKlayOutside;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface WalletUseCase {
    void useWallet(Wallet wallet);

    void registerWallet(Member member, CheckPwd checkPwd)
        throws IOException, ParseException, InterruptedException;

    void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException;

    void swapDidaToKlay(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException;

    void sendKlayOutside(Member member, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException;
}
