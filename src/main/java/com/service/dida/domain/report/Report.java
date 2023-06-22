package com.service.dida.domain.report;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "report")
public class Report extends BaseEntity {
    @Id
    @Column(name = "reportId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    @Column(name = "reportedId", nullable = false)
    private Long reportedId;
    @Column(name = "type", nullable = false)
    private ReportType type;
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
}
