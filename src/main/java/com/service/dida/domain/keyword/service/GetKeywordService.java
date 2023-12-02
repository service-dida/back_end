package com.service.dida.domain.keyword.service;

import com.service.dida.domain.keyword.Keyword;
import com.service.dida.domain.keyword.dto.KeywordResponseDto.GetKeywordList;
import com.service.dida.domain.keyword.repository.KeywordRepository;
import com.service.dida.domain.keyword.usecase.GetKeywordUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetKeywordService implements GetKeywordUseCase {

    private final KeywordRepository keywordRepository;


    @Override
    public GetKeywordList getKeywordList() {
        List<String> things = new ArrayList<>();
        List<String> places = new ArrayList<>();
        List<Keyword> keywords = keywordRepository.findAll();
        keywords.forEach(keyword -> {
            if (keyword.getType() == 1) {
                things.add(keyword.getName());
            } else if (keyword.getType() == 2) {
                places.add(keyword.getName());
            }
        });
        return new GetKeywordList(things,places);
    }
}
