package com.service.dida.global.common.dto;

import lombok.Data;

@Data
public class PageRequestDto {
    private int page;       // 요청하려는 페이지
    private int pageSize;   // 한 페이지 당 원하는 리소스 수
}
