package com.service.dida.domain.nft.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.SendNftRequestDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface UpdateNftUseCase {
    void deleteNft(Member member,Long nftId);

    void sendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException;
}
