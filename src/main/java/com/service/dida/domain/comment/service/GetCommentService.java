package com.service.dida.domain.comment.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentResponseDto.CommentInfo;
import com.service.dida.domain.comment.dto.CommentResponseDto.GetCommentResponseDto;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 오래된 순서 정렬이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    /**
     * 나의 댓글인지를 나타내는 type 을 반환하는 함수
     */
    public String checkIsMe(Long memberId, Long ownerId) {
        String type;
        if (memberId == 0L) {  // 로그인하지 않았다면
            type = "NEED LOGIN";
        } else if (memberId.equals(ownerId)) {  // 내 게시물이라면
            type = "MINE";
        } else {  // 내 게시물이 아니라면
            type = "NOT MINE";
        }
        return type;
    }

    public GetCommentResponseDto makeGetCommentResForm(Long memberId, Comment comment) {
        CommentInfo commentInfo = new CommentInfo(
                comment.getCommentId(), comment.getContent());

        MemberInfo memberInfo = new MemberInfo(
                comment.getMember().getMemberId(), comment.getMember().getNickname(),
                comment.getMember().getProfileUrl());

        return GetCommentResponseDto.builder()
                .commentInfo(commentInfo)
                .memberInfo(memberInfo)
                .type(checkIsMe(memberId, comment.getMember().getMemberId()))
                .build();
    }

    public PageResponseDto<List<GetCommentResponseDto>> makeCommentListForm(Long memberId, Page<Comment> comments) {
        List<GetCommentResponseDto> res = new ArrayList<>();

        for(Comment c: comments.getContent()) {
            res.add(makeGetCommentResForm(memberId, c));
        }
        return new PageResponseDto<>(
                comments.getNumber(), comments.getSize(), comments.hasNext(), res);
    }

    /**
     * [임시 함수] postId를 받아 미리 보기 댓글 3개 반환하는 함수
     */
    public List<GetCommentResponseDto> getPreviewComments(Long postId) {
        return new ArrayList<>();
    }

    public PageResponseDto<List<GetCommentResponseDto>> getAllComments(Member member, Long postId, PageRequestDto pageRequestDto) {
        postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));
        Page<Comment> comments = commentRepository.findByPostIdWithDeleted(postId, pageReq(pageRequestDto));
        return makeCommentListForm(member.getMemberId(), comments);
    }

}
