package com.service.dida.domain.hide.post_hide.service;

import com.service.dida.domain.hide.post_hide.PostHide;
import com.service.dida.domain.hide.post_hide.repository.PostHideRepository;
import com.service.dida.domain.hide.post_hide.usecase.RegisterPostHideUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.HideErrorCode;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPostHideService implements RegisterPostHideUseCase {

    private final PostHideRepository hideRepository;
    private final PostRepository postRepository;

    @Transactional
    public void save(PostHide hide) {
        hideRepository.save(hide);
    }

    public void createPostHide(Member member, Post post) {
        save(PostHide.builder()
                .member(member)
                .post(post)
                .build());
    }

    @Override
    @Transactional
    public void hidePost(Member member, Long postId) {
        Post post = postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));
        if (hideRepository.findByMemberAndPost(member, post).isEmpty()) {
            createPostHide(member, post);
        } else {
            throw new BaseException(HideErrorCode.ALREADY_HIDE);
        }
    }
}
