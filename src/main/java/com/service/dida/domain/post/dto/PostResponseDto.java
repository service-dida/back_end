package com.service.dida.domain.post.dto;

import com.service.dida.domain.comment.dto.CommentResponseDto;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.nft.dto.NftResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostResponseDto {

    @Getter
    @AllArgsConstructor
    public static class PostInfo {
        private Long postId;
        private String title;
        private String content;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class GetPostResponseDto {
        private PostInfo postInfo;                            // 게시글 관련 정보
        private MemberResponseDto.MemberInfo memberInfo;      // 게시글 작성자 관련 정보
        private NftResponseDto.NftInfo nftInfo;               // NFT 관련 정보
        private String type;                                  // 본인의 게시물인지
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class GetPostsResponseDto {
        private GetPostResponseDto getPostResponseDto;
        // 미리보기 댓글, 최대 3개
        private List<CommentResponseDto.GetCommentsResponseDto> comments;
    }


}
