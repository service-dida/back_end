package com.service.dida.domain.post.service;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostPostRequestDto;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.RegisterPostUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterPostService implements RegisterPostUseCase {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final NftRepository nftRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void createPost(Member member, PostPostRequestDto postPostRequestDto) {
        Nft nft = nftRepository.findByNftIdWithDeleted(postPostRequestDto.getNftId())
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));

        Post post = Post.builder()
                .title(postPostRequestDto.getTitle())
                .content(postPostRequestDto.getContent())
                .member(member)
                .nft(nft)
                .build();

        save(post);
    }


}
