package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements ErrorCode {
    SELF_REPORT("REPORT_001", "자기 자신은 신고할 수 없습니다.",HttpStatus.CONFLICT),
    ALREADY_REPORT("REPORT_002", "이미 신고한 대상입니다.",HttpStatus.CONFLICT)
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
