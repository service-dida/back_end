package com.service.dida.domain.history.service;

import com.service.dida.domain.history.History;
import com.service.dida.domain.history.dto.HistoryResponseDto.NftOwnData;
import com.service.dida.domain.history.dto.HistoryResponseDto.NftOwnHistory;
import com.service.dida.domain.history.repository.HistoryRepository;
import com.service.dida.domain.history.usecase.GetHistoryUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHistoryService implements GetHistoryUseCase {

    private final HistoryRepository historyRepository;

    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
            , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public NftOwnHistory getNftOwnHistory(Long nftId, PageRequestDto pageRequestDto) {
        List<NftOwnData> nftOwnHistory = new ArrayList<>();
        Page<History> histories = historyRepository.findAllByNftId(nftId, pageReq(pageRequestDto));
        histories.forEach(h -> nftOwnHistory.add(
            new NftOwnData(h.getCreatedAt(), h.getMember().getMemberId(),
                h.getMember().getNickname())));

        return new NftOwnHistory(nftOwnHistory);
    }
}
