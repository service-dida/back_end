package com.service.dida.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.service.dida.domain.comment.dto.CommentResponseDto.GetCommentsResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentService {

    /**
     * [임시 함수] postId를 받아 미리 보기 댓글 3개 반환하는 함수
     */
    public List<GetCommentsResponseDto> getPreviewComments(Long postId) {
        return new ArrayList<>();
    }
}
