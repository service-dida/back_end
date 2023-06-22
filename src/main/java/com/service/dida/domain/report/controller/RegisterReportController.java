package com.service.dida.domain.report.controller;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.report.dto.ReportRequestDto.RegisterReport;
import com.service.dida.domain.report.usecase.RegisterReportUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.security.auth.CurrentMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterReportController {

    private RegisterReportUseCase registerReportUseCase;

    /**
     * 유저 신고하기
     * [POST] /common/report/user
     */
    @ResponseBody
    @PostMapping("/common/report/user")
    public ResponseEntity<Integer> registerReportUser(@CurrentMember Member member,
            @RequestBody @Valid RegisterReport registerReport) throws BaseException {
        registerReportUseCase.registerReportUser(member, registerReport);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 게시글 신고하기
     * [POST] /common/report/post
     */
    @ResponseBody
    @PostMapping("/common/report/post")
    public ResponseEntity<Integer> registerReportPost(@CurrentMember Member member,
                                                      @RequestBody @Valid RegisterReport registerReport) throws BaseException {
        registerReportUseCase.registerReportPost(member, registerReport);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * 댓글 신고하기
     * [POST] /common/report/comment
     */
    @ResponseBody
    @PostMapping("/common/report/comment")
    public ResponseEntity<Integer> registerReportComment(@CurrentMember Member member,
                                                      @RequestBody @Valid RegisterReport registerReport) throws BaseException {
        registerReportUseCase.registerReportComment(member, registerReport);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    /**
     * NFT 신고하기
     * [POST] /common/report/nft
     */
    @ResponseBody
    @PostMapping("/common/report/nft")
    public ResponseEntity<Integer> registerReportNft(@CurrentMember Member member,
                                                         @RequestBody @Valid RegisterReport registerReport) throws BaseException {
        registerReportUseCase.registerReportNft(member, registerReport);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
