package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface RegisterNftUseCase {

    void registerNft(Member member, PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException;
}
