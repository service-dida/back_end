package com.service.dida.domain.comment.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentRequestDto.PostCommentRequestDto;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.comment.usecase.RegisterCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterCommentService implements RegisterCommentUseCase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void registerComment(Member member, PostCommentRequestDto postCommentRequestDto) {
        Post post = postRepository.findByPostIdWithDeleted(postCommentRequestDto.getPostId()).orElse(null);

        Comment comment = Comment.builder()
                .content(postCommentRequestDto.getContent())
                .member(member)
                .post(post)
                .build();

        save(comment);
    }
}
