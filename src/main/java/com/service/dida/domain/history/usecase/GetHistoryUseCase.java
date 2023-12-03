package com.service.dida.domain.history.usecase;

import com.service.dida.domain.history.dto.HistoryResponseDto.NftOwnHistory;
import com.service.dida.global.common.dto.PageRequestDto;

public interface GetHistoryUseCase {
    NftOwnHistory getNftOwnHistory(Long nftId, PageRequestDto pageRequestDto);
}
