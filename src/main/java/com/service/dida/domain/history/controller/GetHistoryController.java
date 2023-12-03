package com.service.dida.domain.history.controller;

import com.service.dida.domain.history.dto.HistoryResponseDto.NftOwnHistory;
import com.service.dida.domain.history.usecase.GetHistoryUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHistoryController {

    private final GetHistoryUseCase getHistoryUseCase;

    /**
     * NFT 소유권 내역 확인하기
     */
    @GetMapping("/history/{nftId}")
    public ResponseEntity<NftOwnHistory> getNftOwnHistory(@RequestParam("page") int page,
        @RequestParam("size") int size, @PathVariable Long nftId) {
        return new ResponseEntity<>(
            getHistoryUseCase.getNftOwnHistory(nftId, new PageRequestDto(page, size)),
            HttpStatus.OK);
    }
}
