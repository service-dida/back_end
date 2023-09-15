package com.service.dida.domain.like.controller;

import com.service.dida.domain.like.dto.LikeRequestDto;
import com.service.dida.domain.like.usecase.RegisterLikeUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RegisterLikeController {

    private final RegisterLikeUseCase registerLikeUseCase;

    /**
     * 좋아요 누르기
     * [POST] /common/nft/like
     */
    @PostMapping("/common/nft/like")
    public ResponseEntity<Boolean> pushLike(
            @RequestBody LikeRequestDto.NftLikeRequestDto nftLikeRequestDto, @CurrentMember Member member)
            throws BaseException, IOException {
        return new ResponseEntity<>(registerLikeUseCase.pushLike(member, nftLikeRequestDto), HttpStatus.OK);
    }

}
