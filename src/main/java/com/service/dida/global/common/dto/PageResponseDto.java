package com.service.dida.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {
    private int page;           // 현재 페이지
    private int pageSize;       // 한 페이지 당 리소스의 개수
    private boolean hasNext;    // 다음 페이지가 존재하는지, 존재하면 true
    private T response;         // 리소스 리스트

}
