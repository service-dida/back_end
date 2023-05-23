package com.service.dida.config.exception.errorCode;

import com.service.dida.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    EMPTY_USER("USER_001", "존재하지 않는 사용자입니다.")
    ;
    private final String errorCode;
    private final String message;
}
