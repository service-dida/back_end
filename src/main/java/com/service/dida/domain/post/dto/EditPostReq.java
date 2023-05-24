package com.service.dida.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EditPostReq {
    @NotBlank(message = "수정하려는 게시글의 Id를 입력하세요.")
    private Long postId;
    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 2, max = 30, message = "제목의 길이는 2~20이어야 합니다.")
    private String title;
    @NotBlank(message = "본문을 입력하세요.")
    @Size(min = 2, max = 300, message = "본문의 길이는 2~300이어야 합니다.")
    private String content;
}
