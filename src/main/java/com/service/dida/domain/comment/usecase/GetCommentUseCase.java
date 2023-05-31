package com.service.dida.domain.comment.usecase;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GetCommentUseCase {
    CommentResponseDto.GetCommentResponseDto makeGetCommentResForm(Member member, Comment comment, boolean needType);
    PageResponseDto<List<CommentResponseDto.GetCommentResponseDto>> makeCommentListForm(Member member, Page<Comment> comments);
    List<CommentResponseDto.GetCommentResponseDto> getPreviewComments(Long postId);
    PageResponseDto<List<CommentResponseDto.GetCommentResponseDto>> getAllComments(Member member, Long postId, PageRequestDto pageRequestDto);
    String checkIsMe(Member member, Member owner);

}
