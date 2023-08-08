package com.service.dida.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EditPostRequestDto {
    @NotNull(message = "수정하려는 게시글의 Id를 입력하세요.")
    private Long postId;
    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 200, message = "제목의 길이는 1~200이어야 합니다.")
    private String title;
    @NotBlank(message = "본문을 입력하세요.")
    @Size(min = 1, max = 300, message = "본문의 길이는 1~300이어야 합니다.")
    private String content;
}
