package com.service.dida.domain.follow.controller;

import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.follow.usecase.RegisterFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final GetFollowUseCase getFollowUseCase;
    private final RegisterFollowUseCase registerFollowUseCase;

    /**
     * 팔로우 누르기 Api
     */
    @PatchMapping("/member/follow/{memberId}")
    public ResponseEntity<Integer> pushFollow(@CurrentMember Member member,
        @PathVariable(name = "memberId") Long memberId) {
        registerFollowUseCase.registerFollow(member, memberId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
