package com.service.dida.domain.comment.service;

import com.service.dida.domain.alarm.usecase.RegisterAlarmUseCase;
import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.dto.CommentRequestDto.PostCommentRequestDto;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.comment.usecase.RegisterCommentUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Transactional
public class RegisterCommentService implements RegisterCommentUseCase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final RegisterAlarmUseCase registerAlarmUseCase;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void registerComment(Member member, PostCommentRequestDto postCommentRequestDto) throws IOException {
        Post post = postRepository.findByPostIdWithDeleted(postCommentRequestDto.getPostId())
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));

        Comment comment = Comment.builder()
                .content(postCommentRequestDto.getContent())
                .member(member)
                .post(post)
                .build();

        save(comment);
        registerAlarmUseCase.registerCommentAlarm(post.getMember(), comment.getCommentId());
    }
}
