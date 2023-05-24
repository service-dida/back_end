package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMPTY_JWT("AUTH_001", "JWT가 없습니다."),
    INVALID_JWT("AUTH_002", "유효하지 않은 JWT입니다."),
    EXPIRED_MEMBER_JWT("AUTH_003", "만료된 JWT입니다."),
    UNSUPPORTED_JWT("AUTH_004", "지원하지 않는 JWT입니다."),
    INVALID_ID_TOKEN("AUTH_005","유효하지 않은 ID TOKEN입니다."),
    EMPTY_ID_TOKEN("AUTH_006","ID TOKEN이 없습니다."),
    FAILED_APPLE_LOGIN("AUTH_007","APPLE LOGIN에 실패하였습니다.")
    ;
    private final String errorCode;
    private final String message;
}
