package com.service.dida.domain.post.controller;


import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.dto.PostPostRequestDto;
import com.service.dida.domain.post.usecase.RegisterPostUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterPostController {
    private final RegisterPostUseCase registerPostUseCase;

    /**
     * 게시글 생성하기
     * [POST] /common/posts
     */
    @PostMapping("/common/posts")
    public ResponseEntity<Integer> createPost(
            @RequestBody @Valid PostPostRequestDto postPostRequestDto,
            @CurrentMember Member member)
            throws BaseException {
        registerPostUseCase.createPost(member, postPostRequestDto);
        return new ResponseEntity<Integer>(200, HttpStatus.OK);
    }

}
