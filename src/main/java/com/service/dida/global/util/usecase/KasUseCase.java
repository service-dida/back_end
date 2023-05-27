package com.service.dida.global.util.usecase;

import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface KasUseCase {
    String createAccount() throws IOException, ParseException, InterruptedException;

    String uploadMetadata(PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException;

    String createNft(String address, String id, String uri)
        throws IOException, ParseException, InterruptedException;
}
