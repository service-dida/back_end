package com.service.dida.domain.report.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.report.dto.ReportRequestDto;

public interface RegisterReportUseCase {
    void registerReportMember(Member member, ReportRequestDto.RegisterReport registerReport);
    void registerReportPost(Member member, ReportRequestDto.RegisterReport registerReport);
    void registerReportComment(Member member, ReportRequestDto.RegisterReport registerReport);
    void registerReportNft(Member member, ReportRequestDto.RegisterReport registerReport);
}
