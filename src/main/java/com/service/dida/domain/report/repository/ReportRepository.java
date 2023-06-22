package com.service.dida.domain.report.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.report.Report;
import com.service.dida.domain.report.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByMemberAndReportedIdAndType(Member member, Long reportedId, ReportType type);
}
