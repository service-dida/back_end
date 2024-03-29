package com.service.dida.domain.keyword.controller;

import com.service.dida.domain.keyword.dto.KeywordResponseDto.GetKeywordList;
import com.service.dida.domain.keyword.usecase.GetKeywordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final GetKeywordUseCase getKeywordUseCase;

    /**
     * 키워드 가져오기
     * [GET] /keyword
     */
    @GetMapping("/keyword")
    public ResponseEntity<GetKeywordList> getKeywordList() {
        return new ResponseEntity<>(getKeywordUseCase.getKeywordList(), HttpStatus.OK);
    }
}
