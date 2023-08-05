package com.service.dida.domain.comment.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentResponseDto.CommentInfo;
import com.service.dida.domain.comment.dto.CommentResponseDto.GetCommentResponseDto;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.comment.usecase.GetCommentUseCase;
import com.service.dida.domain.hide.comment_hide.repository.CommentHideRepository;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetCommentService implements GetCommentUseCase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentHideRepository commentHideRepository;

    /**
     * 댓글 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    /**
     * 나의 댓글인지를 나타내는 type 을 반환하는 함수
     */
    @Override
    public String checkIsMe(Member member, Member owner) {
        String type;
        if (member == null) {  // 로그인하지 않았다면
            type = "NEED LOGIN";
        } else if (Objects.equals(member.getMemberId(), owner.getMemberId())) {  // 내 게시물이라면
            type = "MINE";
        } else {  // 내 게시물이 아니라면
            type = "NOT MINE";
        }
        return type;
    }

    @Override
    public GetCommentResponseDto makeGetCommentResForm(Member member, Comment comment, boolean needType) {
        CommentInfo commentInfo = new CommentInfo(
                comment.getCommentId(), comment.getContent());

        MemberInfo memberInfo = new MemberInfo(
                comment.getMember().getMemberId(), comment.getMember().getNickname(),
                comment.getMember().getProfileUrl());

        String type = "";
        if (needType) {
            type = checkIsMe(member, comment.getMember());
        }
        return GetCommentResponseDto.builder()
                .commentInfo(commentInfo)
                .memberInfo(memberInfo)
                .type(type)
                .build();
    }

    @Override
    public PageResponseDto<List<GetCommentResponseDto>> makeCommentListForm(Member member, Page<Comment> comments) {
        List<GetCommentResponseDto> res = new ArrayList<>();
        comments.forEach(c -> res.add(makeGetCommentResForm(member, c, true)));
        return new PageResponseDto<>(
                comments.getNumber(), comments.getSize(), comments.hasNext(), res);
    }

    /**
     * postId를 받아 미리 보기 댓글을 commentCounts 만큼 반환하는 함수
     * 페이징 처리 필요 X, 본인 댓글 여부 필요 X
     * 최신순 조회 O
     */
    @Override
    public List<GetCommentResponseDto> getPreviewComments(Member member, Long postId) {
        Post post = postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));

        List<Comment> comments = post.getComments();
        List<GetCommentResponseDto> res = new ArrayList<>();

        // 미리보기로 받을 댓글 개수
        int commentCounts = 3;
        for (int i = comments.size() - 1; i >= 0; i--) {
            if (commentCounts-- == 0) break;
            Comment c = comments.get(i);
            if (commentHideRepository.findByMemberAndComment(member, c).isPresent()) continue;
            res.add(makeGetCommentResForm(null, c, false));
        }
        return res;
    }

    /**
     * postId를 받아 댓글을 페이징 처리하여 반환하는 함수
     */
    @Override
    public PageResponseDto<List<GetCommentResponseDto>> getAllComments(Member member, Long postId, PageRequestDto pageRequestDto) {
        postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));
        Page<Comment> comments;
        if (member != null) {
            comments = commentRepository.findByPostIdWithDeletedWithoutHide(member, postId, pageReq(pageRequestDto));
        } else {
            comments = commentRepository.findByPostIdWithDeleted(postId, pageReq(pageRequestDto));
        }
        return makeCommentListForm(member, comments);
    }

}
