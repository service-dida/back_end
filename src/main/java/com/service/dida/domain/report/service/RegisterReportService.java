package com.service.dida.domain.report.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.report.Report;
import com.service.dida.domain.report.ReportType;
import com.service.dida.domain.report.dto.ReportRequestDto.RegisterReport;
import com.service.dida.domain.report.repository.ReportRepository;
import com.service.dida.domain.report.usecase.RegisterReportUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.ReportErrorCode;
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
    @Override
    @Transactional
    public void registerReportUser(Member member, RegisterReport registerReport) {
        Member reportedMember = memberRepository.findByMemberIdWithDeleted((registerReport.getReportedId()))
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));

        checkSelfReport(member.getMemberId(), registerReport.getReportedId());

        // DB에 신고 기록이 없다면
        if(!reportRepository.existsByMemberAndReportedIdAndType(member, reportedMember.getMemberId(), ReportType.USER)) {
            createReport(member, registerReport, ReportType.USER);
            // 누적 신고 횟수가 기준치 이상이라면
            if(reportedMember.getReportCnt() >= standard) {
                // 숨김 처리 추가 필요

                // 삭제 처리 및 이메일 전송
                reportedMember.changeDeleted(true);
                mailUseCase.sendReportMail(reportedMember.getEmail());
            }
        } else {
            throw new BaseException(ReportErrorCode.ALREADY_REPORT);
        }

    }
}
