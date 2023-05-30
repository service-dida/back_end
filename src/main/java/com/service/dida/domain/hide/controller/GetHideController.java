package com.service.dida.domain.hide.controller;

import com.service.dida.domain.hide.dto.HideResponseDto.GetHideNft;
import com.service.dida.domain.hide.usecase.GetHideUseCase;
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
public class GetHideController {
    private final GetHideUseCase getHideUseCase;

    /**
     * NFT 숨김 목록 조회
     * [GET] /nft/hide
     */
    @GetMapping("nft/hide")
    public ResponseEntity<PageResponseDto<List<GetHideNft>>> getHideNftList(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getHideUseCase.getHideNftList(member, pageRequestDto), HttpStatus.OK);
    }
}
