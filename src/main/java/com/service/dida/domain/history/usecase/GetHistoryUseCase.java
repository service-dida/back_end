package com.service.dida.domain.history.usecase;

import com.service.dida.domain.history.dto.HistoryResponseDto.NftOwnData;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.List;

public interface GetHistoryUseCase {
    PageResponseDto<List<NftOwnData>> getNftOwnHistory(Long nftId, PageRequestDto pageRequestDto);
}
