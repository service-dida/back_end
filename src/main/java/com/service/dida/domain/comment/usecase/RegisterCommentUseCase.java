package com.service.dida.domain.comment.usecase;

import com.service.dida.domain.comment.dto.CommentRequestDto;
import com.service.dida.domain.member.entity.Member;

public interface RegisterCommentUseCase {
    void registerComment(Member member, CommentRequestDto.PostCommentRequestDto postCommentRequestDto);
}
