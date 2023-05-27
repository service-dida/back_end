package com.service.dida.domain.nft.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NftRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostNftRequestDto {
        @NotEmpty(message = "비밀번호가 일치하지 않습니다.")
        private String payPwd;
        @NotEmpty(message = "제목은 빈값일 수 없습니다.")
        private String title;
        @NotEmpty(message = "설명은 빈값일 수 없습니다.")
        private String description;
        @NotEmpty(message = "이미지는 빈값일 수 없습니다.")
        private String image;
    }
}
