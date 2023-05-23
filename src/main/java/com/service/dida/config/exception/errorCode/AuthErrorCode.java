package com.service.dida.config.exception.errorCode;

import com.service.dida.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMPTY_JWT("AUTH_001", "JWT가 없습니다."),
    INVALID_JWT("AUTH_002", "유효하지 않은 JWT입니다."),
    EXPIRED_MEMBER_JWT("AUTH_003", "만료된 JWT입니다."),
    ;
    private final String errorCode;
    private final String message;
}
