package com.service.dida.domain.comment.dto;


import com.service.dida.domain.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class CommentResponseDto {
    @Data
    @AllArgsConstructor
    public static class CommentInfo {
        private Long commentId;                 // 댓글 Id
        private String content;                 // 댓글 내용
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetCommentsResponseDto {
        private CommentInfo commentInfo;
        private MemberResponseDto.MemberInfo memberInfo;  // 댓글 작성자 관련 정보
        private String type;                              // 본인 댓글인지 확인하기 위함
    }
}
