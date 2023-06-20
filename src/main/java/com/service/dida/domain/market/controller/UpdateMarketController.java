package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.usecase.UpdateMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMarketController {

    private final UpdateMarketUseCase updateMarketUseCase;

    /**
     * delete market Api
     */
    @DeleteMapping("/member/market/{marketId}")
    public ResponseEntity<Integer> deleteMarket(@CurrentMember Member member,
        @PathVariable(name = "marketId") Long marketId) {
        updateMarketUseCase.deleteMarket(member, marketId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
