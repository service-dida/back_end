package com.service.dida.domain.market.controller;

import com.service.dida.domain.market.service.GetMarketService;
import com.service.dida.domain.market.dto.GetMainPageWithoutSoldOut;
import com.service.dida.global.config.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMarketController {
    private final GetMarketService marketService;

    /**
     * 메인 화면 가져오기 (Sold Out 제외) [GET] /main
     */
    @GetMapping("/main")
    public ResponseEntity<GetMainPageWithoutSoldOut> getMainPage() throws BaseException {
        Long memberId = 0L;
        return new ResponseEntity<>(marketService.getMainPage(memberId),
            HttpStatus.OK);
    }
}
