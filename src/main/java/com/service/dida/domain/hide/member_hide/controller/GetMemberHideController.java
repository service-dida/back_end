package com.service.dida.domain.hide.member_hide.controller;

import com.service.dida.domain.hide.member_hide.usecase.GetMemberHideUseCase;
import com.service.dida.domain.hide.member_hide.dto.MemberHideResponseDto.GetMemberHide;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetMemberHideController {

    private final GetMemberHideUseCase getMemberHideUseCase;

    /**
     * Member 숨김 목록 조회
     * [GET] /common/member/hide
     */
    @GetMapping("/common/member/hide")
    public ResponseEntity<PageResponseDto<List<GetMemberHide>>> getNftHideNftList(
            @CurrentMember Member member, @RequestBody PageRequestDto pageRequestDto)
            throws BaseException {
        return new ResponseEntity<>(getMemberHideUseCase.getMemberHideList(member, pageRequestDto), HttpStatus.OK);
    }
}
