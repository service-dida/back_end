package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    EMPTY_MEMBER("MEMBER_001", "존재하지 않는 사용자입니다."),
    UN_REGISTERED_MEMBER("MEMBER_002", ""),
    DUPLICATE_MEMBER("MEMBER_003", "중복된 사용자입니다."),
    DUPLICATE_NICKNAME("MEMBER_004", "중복된 닉네임입니다.");

    private final String errorCode;
    private final String message;
}
