package com.service.dida.domain.market;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.domain.market.dto.GetMainPageWithoutSoldOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    /**
     * 메인 화면 가져오기 (Sold Out 제외)
     * [GET] /main
     */
    @GetMapping("/main")
    public ResponseEntity<GetMainPageWithoutSoldOut> getMainPage() throws BaseException {
        Long userId = 0L;
        return new ResponseEntity<GetMainPageWithoutSoldOut>(marketService.getMainPage(userId), HttpStatus.OK);
    }
}
