package com.service.dida.domain.hide.nft_hide.controller;

import com.service.dida.domain.hide.nft_hide.usecase.GetNftHideUseCase;
import com.service.dida.domain.hide.nft_hide.dto.NftHideResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetNftHideController {
    private final GetNftHideUseCase getNftHideUseCase;

    /**
     * NFT 숨김 목록 조회
     * [GET] /common/nft/hide
     */
    @GetMapping("/common/nft/hide")
    public ResponseEntity<PageResponseDto<List<NftHideResponseDto.GetNftHide>>> getNftHideNftList(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getNftHideUseCase.getNftHideList(member, pageRequestDto), HttpStatus.OK);
    }
}
