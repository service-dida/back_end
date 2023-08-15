package com.service.dida.domain.follow.controller;

import com.service.dida.domain.follow.dto.FollowResponseDto.FollowList;
import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.follow.usecase.RegisterFollowUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final GetFollowUseCase getFollowUseCase;
    private final RegisterFollowUseCase registerFollowUseCase;

    /**
     * 팔로우 누르기 Api
     */
    @PatchMapping("/common/follow/{memberId}")
    public ResponseEntity<Integer> pushFollow(@CurrentMember Member member,
        @PathVariable(name = "memberId") Long memberId) throws IOException {
        registerFollowUseCase.registerFollow(member, memberId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 내 팔로우 목록 보기
     */
    @GetMapping("/common/follow")
    public ResponseEntity<PageResponseDto<List<FollowList>>> getFollowerList(
            @CurrentMember Member member,
            @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(getFollowUseCase.getFollowerList(member,
                new PageRequestDto(page, size)),
            HttpStatus.OK);
    }

    /**
     * 내 팔로잉 목록 보기
     */
    @GetMapping("/common/following")
    public ResponseEntity<PageResponseDto<List<FollowList>>> getFollowingList(
        @CurrentMember Member member,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(getFollowUseCase.getFollowingList(member,
                new PageRequestDto(page, size)),
            HttpStatus.OK);
    }
}
