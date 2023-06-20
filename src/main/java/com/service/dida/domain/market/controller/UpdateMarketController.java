package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.dto.MarketRequestDto.UpdateMarket;
import com.service.dida.domain.market.usecase.UpdateMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateMarketController {

    private final UpdateMarketUseCase updateMarketUseCase;

    /**
     * delete market Api
     */
    @DeleteMapping("/member/market")
    public ResponseEntity<Integer> deleteMarket(@CurrentMember Member member,
        @RequestBody UpdateMarket updateMarket) {
        updateMarketUseCase.deleteMarket(member, updateMarket);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
