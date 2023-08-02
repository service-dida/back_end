package com.service.dida.global.util.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiPictureDto {
    private String url1;
    private String url2;
    private String url3;
    private String url4;

    public AiPictureDto(List<String> urls) {
        this.url1 = urls.get(0);
        this.url2 = urls.get(1);
        this.url3 = urls.get(2);
        this.url4 = urls.get(3);
    }
}
