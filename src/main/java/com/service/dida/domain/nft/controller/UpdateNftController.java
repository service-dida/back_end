package com.service.dida.domain.nft.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.SendNftRequestDto;
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
public class UpdateNftController {

    private final UpdateNftUseCase updateNftUseCase;

    /**
     * NFT 삭제 Api
     */
    @DeleteMapping("/member/nft/{nftId}")
    public ResponseEntity<Integer> deleteNft(@CurrentMember Member member,
        @PathVariable(value = "nftId") Long nftId) {
        updateNftUseCase.deleteNft(member, nftId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * NFT 전송하기 Api
     */
    @PostMapping("/member/nft/address")
    public ResponseEntity<Integer> sendNftOutside(@CurrentMember Member member,
        @RequestBody SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        updateNftUseCase.sendNftOutside(member, sendNftRequestDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
