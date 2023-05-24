package com.service.dida.domain.post.service;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostPostReq;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.user.User;
import com.service.dida.domain.user.repository.UserRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final NftRepository nftRepository;

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void createPost(Long userId, PostPostReq postPostReq) {
        User user = userRepository.findByUserId(userId).orElse(null);
        if(!user.isValidated()) {
            throw new BaseException(UserErrorCode.EMPTY_USER);
        }
        Nft nft = nftRepository.findByNftId(postPostReq.getNftId()).orElse(null);
        if(nft.isValidated()) {
            throw new BaseException(NftErrorCode.EMPTY_NFT);
        }

        Post post = Post.builder()
                .title(postPostReq.getTitle())
                .content(postPostReq.getContent())
                .user(user)
                .nft(nft)
                .build();

        save(post);
    }

}
