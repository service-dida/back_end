package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AlarmErrorCode implements ErrorCode {
    INVALID_ID("ALARM_001", "없거나 이미 본 ALARM ID 입니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}