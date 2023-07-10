package com.service.dida.domain.hide.nft_hide.controller;

import com.service.dida.domain.hide.nft_hide.usecase.UpdateNftHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UpdateNftHideController {

    private final UpdateNftHideUseCase updateNftHideUseCase;

    /**
     * NFT 숨기기 취소
     * [DELETE] /common/nft/hide
     */
    @DeleteMapping("/common/nft/hide")
    public ResponseEntity<Integer> unhideNft(
            @RequestParam("nftId") Long nftId, @CurrentMember Member member)
            throws BaseException {
            updateNftHideUseCase.unhideNft(member, nftId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
