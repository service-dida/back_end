package com.service.dida.domain.comment.usecase;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GetCommentUseCase {
    CommentResponseDto.GetCommentResponseDto makeGetCommentResForm(Long memberId, Comment comment, boolean needType);
    PageResponseDto<List<CommentResponseDto.GetCommentResponseDto>> makeCommentListForm(Long memberId, Page<Comment> comments);
    List<CommentResponseDto.GetCommentResponseDto> getPreviewComments(Long postId);
    PageResponseDto<List<CommentResponseDto.GetCommentResponseDto>> getAllComments(Member member, Long postId, PageRequestDto pageRequestDto);
}
