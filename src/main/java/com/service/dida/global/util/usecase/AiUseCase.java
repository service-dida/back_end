package com.service.dida.global.util.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.DrawPictureRequestDto;
import com.service.dida.global.util.dto.AiPictureDto;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface AiUseCase {
    AiPictureDto drawPicture(Member member, DrawPictureRequestDto drawPictureRequestDto)
        throws IOException, ParseException, InterruptedException;
}
