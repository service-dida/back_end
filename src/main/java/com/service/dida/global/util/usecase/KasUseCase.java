package com.service.dida.global.util.usecase;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.wallet.Wallet;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface KasUseCase {
    String createAccount() throws IOException, ParseException, InterruptedException;

    String uploadMetadata(PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException;

    String createNft(String address, String id, String uri)
        throws IOException, ParseException, InterruptedException;

    double getKlay(Wallet wallet) throws IOException, ParseException, InterruptedException;

//    double getDida(Wallet wallet);

    String mintDida(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendKlayToLiquidPool(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendKlayToFeeAccount(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

//    String sendDida(Wallet sender, Wallet receiver, double coin);

//    String sendNft(Wallet sender, Wallet receiver, Nft nft);

    String sendNftOutside(String sendAddress, String receiveAddress, String id)
        throws IOException, ParseException, InterruptedException;
}
