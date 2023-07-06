package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangeCoin;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendKlayOutside;
import com.service.dida.domain.wallet.dto.WalletRequestDto.SendNftRequestDto;
import com.service.dida.domain.wallet.dto.WalletResponseDto.WalletDetail;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface WalletUseCase {
    WalletDetail getWalletDetail(Member member)
        throws IOException, ParseException, InterruptedException;

    void useWallet(Wallet wallet);

    void registerWallet(Member member, CheckPwd checkPwd)
        throws IOException, ParseException, InterruptedException;

    void swapKlayToDida(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException;

    void swapDidaToKlay(Member member, ChangeCoin changeCoin)
        throws IOException, ParseException, InterruptedException;

    void sendKlayOutside(Member member, SendKlayOutside sendKlayOutside)
        throws IOException, ParseException, InterruptedException;

    void sendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException;

    void purchaseNftInMarket(Member buyer, String payPwd, Market market)
        throws IOException, ParseException, InterruptedException;
}
