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

    double getDida(Wallet wallet) throws IOException, ParseException, InterruptedException;

    String mintDida(Wallet wallet, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendKlayToLiquidPool(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendKlayToFeeAccount(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendKlayFromLiquidPoolToUser(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendDidaToLiquidPool(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendDidaToFeeAccount(Wallet sender, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendDidaToSeller(Wallet sender,Wallet receiver, double coin)
        throws IOException, ParseException, InterruptedException;

    String sendNft(Wallet sender, Wallet receiver, Nft nft)
        throws IOException, ParseException, InterruptedException;

    String sendNftOutside(String sendAddress, String receiveAddress, String id)
        throws IOException, ParseException, InterruptedException;

    String sendKlayOutside(String sendAddress, String receiveAddress, double coin)
        throws IOException, ParseException, InterruptedException;
}
