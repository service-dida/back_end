package com.service.dida.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CommentRequestDto {

    @Data
    public static class PostCommentRequestDto {
        @NotBlank(message = "postId는 비어있을 수 없습니다.")
        private Long postId;
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        private String content;
    }
}
