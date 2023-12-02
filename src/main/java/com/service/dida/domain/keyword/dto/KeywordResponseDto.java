package com.service.dida.domain.keyword.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KeywordResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetKeywordList {
        List<String> things;
        List<String> places;
    }
}
