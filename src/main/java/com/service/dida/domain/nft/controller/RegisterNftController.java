package com.service.dida.domain.nft.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.nft.usecase.RegisterNftUseCase;
import com.service.dida.domain.nft.usecase.UpdateNftUseCase;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterNftController {

    private final RegisterNftUseCase registerNftUseCase;
    private final UpdateNftUseCase updateNftUseCase;

    /**
     * NFT 생성 Api 사용료 부분 없음, Ai 기능 추가시 조금 수정 필요
     */
    @PostMapping("/member/nft")
    public ResponseEntity<Integer> registerNft(@CurrentMember Member member, @RequestBody
        PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        registerNftUseCase.registerNft(member, postNftRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * NFT 삭제 Api
     */
    @DeleteMapping("/member/nft/{nftId}")
    public ResponseEntity<Integer> deleteNft(@CurrentMember Member member,
        @PathVariable(value = "nftId") Long nftId) {
        updateNftUseCase.deleteNft(member, nftId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

}
