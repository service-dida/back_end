package com.service.dida.domain.post.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.EditPostRequestDto;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.UpdatePostUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePostService implements UpdatePostUseCase {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 나의 게시글인지 체크하는 함수
     */
    public boolean checkIsMe(Long memberId, Long ownerId) {
        memberRepository.findByMemberIdWithDeleted((memberId))
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));

        if (memberId.equals(ownerId)) {
            return true;
        } else {
            throw new BaseException(PostErrorCode.NOT_OWN_POST);
        }
    }

    @Override
    @Transactional
    public void editPost(Long memberId, EditPostRequestDto editPostRequestDto) {
        Post post = postRepository.findByPostIdWithDeleted(editPostRequestDto.getPostId())
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));

        if (checkIsMe(memberId, post.getMember().getMemberId())) {
            post.editPost(editPostRequestDto.getTitle(), editPostRequestDto.getContent());
        }
    }

    @Override
    @Transactional
    public void deletePost(Long memberId, Long postId) {
        Post post = postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));

        if (checkIsMe(memberId, post.getMember().getMemberId())) {
            post.setDeleted();
        }
    }

}
