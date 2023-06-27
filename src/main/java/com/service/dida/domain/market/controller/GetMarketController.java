package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.dto.MarketResponseDto.GetMainPageWithoutSoldOut;
import com.service.dida.domain.market.usecase.GetMarketUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMarketController {
    private final GetMarketUseCase getMarketUseCase;

    /**
     * 메인 화면 가져오기 (Sold Out 제외)
     * [GET] /main
     */
    @GetMapping("/main")
    public ResponseEntity<GetMainPageWithoutSoldOut> getMainPage(@CurrentMember Member member)
            throws BaseException {
        return new ResponseEntity<>(getMarketUseCase.getMainPage(member), HttpStatus.OK);
    }
}
