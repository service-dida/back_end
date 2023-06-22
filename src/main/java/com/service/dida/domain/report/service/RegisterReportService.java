package com.service.dida.domain.report.service;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.report.Report;
import com.service.dida.domain.report.ReportType;
import com.service.dida.domain.report.dto.ReportRequestDto.RegisterReport;
import com.service.dida.domain.report.repository.ReportRepository;
import com.service.dida.domain.report.usecase.RegisterReportUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.*;
import com.service.dida.global.util.mail.MailUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterReportService implements RegisterReportUseCase {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NftRepository nftRepository;
    private final MailUseCase mailUseCase;

    public static final int standard = 15;

    @Transactional
    public void save(Report report) {
        reportRepository.save(report);
    }

    @Transactional
    public void createReport(Member member, RegisterReport registerReport, ReportType reportType) {
        Report report = Report.builder()
                .reportedId(registerReport.getReportedId())
                .description(registerReport.getDescription())
                .type(reportType)
                .member(member)
                .build();
        save(report);
    }

    private void checkSelfReport(Long reporterId, Long reportedId) throws BaseException {
        if (Objects.equals(reporterId, reportedId)) {
            throw new BaseException(ReportErrorCode.SELF_REPORT);
        }
    }

    private void checkAlreadyReport(Member member, Long reportedId, ReportType type) {
        if (reportRepository.existsByMemberAndReportedIdAndType(member, reportedId, type)) {
            throw new BaseException(ReportErrorCode.ALREADY_REPORT);
        }
    }

    @Override
    @Transactional
    public void registerReportUser(Member member, RegisterReport registerReport) {
        Member reportedMember = memberRepository.findByMemberIdWithDeleted((registerReport.getReportedId()))
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));

        checkSelfReport(member.getMemberId(), reportedMember.getMemberId());
        checkAlreadyReport(member, registerReport.getReportedId(), ReportType.USER);

        createReport(member, registerReport, ReportType.USER);
        reportedMember.plusReportCnt();
        // 숨김 처리 추가 필요

        // 누적 신고 횟수가 기준치 이상이라면 유저 임시 삭제 처리 및 이메일 전송
        if (reportedMember.getReportCnt() >= standard) {
            reportedMember.changeDeleted(true);
            mailUseCase.sendReportMail(reportedMember.getEmail());
        }
    }

    @Override
    @Transactional
    public void registerReportPost(Member member, RegisterReport registerReport) {
        Post reportedPost = postRepository.findByPostIdWithDeleted((registerReport.getReportedId()))
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));

        checkSelfReport(member.getMemberId(), reportedPost.getMember().getMemberId());
        checkAlreadyReport(member, registerReport.getReportedId(), ReportType.POST);

        createReport(member, registerReport, ReportType.POST);
        reportedPost.plusReportCnt();
        // 숨김 처리 추가 필요

        // 누적 신고 횟수가 기준치 이상이라면 삭제
        if (reportedPost.getReportCnt() >= standard) {
            reportedPost.changeDeleted(true);
        }
    }

    @Override
    @Transactional
    public void registerReportComment(Member member, RegisterReport registerReport) {
        Comment reportedComment = commentRepository.findByCommentIdWithDeleted((registerReport.getReportedId()))
                .orElseThrow(() -> new BaseException(CommentErrorCode.EMPTY_COMMENT));

        checkSelfReport(member.getMemberId(), reportedComment.getMember().getMemberId());
        checkAlreadyReport(member, registerReport.getReportedId(), ReportType.COMMENT);

        createReport(member, registerReport, ReportType.COMMENT);
        reportedComment.plusReportCnt();
        // 숨김 처리 추가 필요

        // 누적 신고 횟수가 기준치 이상이라면 삭제
        if (reportedComment.getReportCnt() >= standard) {
            reportedComment.changeDeleted(true);
        }
    }

    @Override
    @Transactional
    public void registerReportNft(Member member, RegisterReport registerReport) {
        Nft reportedNft = nftRepository.findByNftIdWithDeleted((registerReport.getReportedId()))
                .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));

        checkSelfReport(member.getMemberId(), reportedNft.getMember().getMemberId());
        checkAlreadyReport(member, registerReport.getReportedId(), ReportType.NFT);

        createReport(member, registerReport, ReportType.NFT);
        reportedNft.plusReportCnt();
        // 숨김 처리 추가 필요

        // 누적 신고 횟수가 기준치 이상이라면 삭제
        if (reportedNft.getReportCnt() >= standard) {
            reportedNft.changeDeleted(true);
        }
    }
}
