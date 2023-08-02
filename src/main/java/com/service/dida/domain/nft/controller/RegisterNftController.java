package com.service.dida.domain.nft.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.DrawPictureRequestDto;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.nft.usecase.RegisterNftUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import com.service.dida.global.util.dto.AiPictureDto;
import com.service.dida.global.util.usecase.AiUseCase;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterNftController {

    private final RegisterNftUseCase registerNftUseCase;
    private final AiUseCase aiUseCase;

    /**
     * NFT 생성 Api 사용료 부분 없음, Ai 기능 추가시 조금 수정 필요
     */
    @PostMapping("/member/nft")
    public ResponseEntity<Integer> registerNft(@CurrentMember Member member,
        @RequestBody PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        registerNftUseCase.registerNft(member, postNftRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * AI로 그림 그리기 API
     */
    @PostMapping("/member/picture")
    public ResponseEntity<AiPictureDto> drawPicture(@CurrentMember Member member,
        @RequestBody DrawPictureRequestDto drawPictureRequestDto)
        throws IOException, ParseException, InterruptedException {
        return new ResponseEntity<>(aiUseCase.drawPicture(member, drawPictureRequestDto),
            HttpStatus.OK);
    }
}
