package com.service.dida.domain.comment.usecase;

import com.service.dida.domain.comment.dto.CommentRequestDto;
import com.service.dida.domain.member.entity.Member;

import java.io.IOException;

public interface RegisterCommentUseCase {
    void registerComment(Member member, CommentRequestDto.PostCommentRequestDto postCommentRequestDto) throws IOException;
}
