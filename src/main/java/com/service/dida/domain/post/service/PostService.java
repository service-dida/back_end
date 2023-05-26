package com.service.dida.domain.post.service;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.EditPostReq;
import com.service.dida.domain.post.dto.PostPostReq;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.PostUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final NftRepository nftRepository;
    @Override
    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void createPost(Long userId, PostPostReq postPostReq) {
        Member member = memberRepository.findByMemberId(userId).orElse(null);
        if (member == null || member.isDeleted()) {
            throw new BaseException(MemberErrorCode.EMPTY_MEMBER);
        }
        Nft nft = nftRepository.findByNftId(postPostReq.getNftId()).orElse(null);
        if (nft == null || nft.isDeleted()) {
            throw new BaseException(NftErrorCode.EMPTY_NFT);
        }

        Post post = Post.builder()
                .title(postPostReq.getTitle())
                .content(postPostReq.getContent())
                .member(member)
                .nft(nft)
                .build();

        save(post);
    }

    /**
     * 나의 게시글인지 체크하는 함수
     */
    @Override
    public boolean checkIsMe(Long userId, Long ownerId) {
        if (userId.equals(ownerId)) {
            return true;
        } else {
            throw new BaseException(PostErrorCode.NOT_OWN_POST);
        }
    }
    @Override
    @Transactional
    public void editPost(Long userId, EditPostReq editPostReq) {
        Post post = postRepository.findByPostId(editPostReq.getPostId()).orElse(null);
        if (post == null || post.isDeleted()) {
            throw new BaseException(PostErrorCode.EMPTY_POST);
        }
        if (checkIsMe(userId, post.getMember().getMemberId())) {
            post.editPost(editPostReq.getTitle(), editPostReq.getContent());
        }
    }

    @Override
    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findByPostId(postId).orElse(null);
        if (post == null || post.isDeleted()) {
            throw new BaseException(PostErrorCode.EMPTY_POST);
        }
        if (checkIsMe(userId, post.getMember().getMemberId())) {
            post.setDeleted();
        }
    }

}
