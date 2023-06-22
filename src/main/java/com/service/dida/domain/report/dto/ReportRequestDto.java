package com.service.dida.domain.report.dto;

import com.service.dida.domain.report.ReportType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequestDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterReport {
        @NotBlank(message = "신고 대상의 ID를 입력해야합니다.")
        private Long reportedId;

        @NotBlank(message = "신고 사유를 입력해야합니다.")
        private String description;
    }
}
