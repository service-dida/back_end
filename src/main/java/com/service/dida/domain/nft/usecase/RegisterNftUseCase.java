package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.nft.dto.NftResponseDto;
import org.json.simple.parser.ParseException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface RegisterNftUseCase {

    NftResponseDto.NftId registerNft(Member member, PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
