package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    EMPTY_MEMBER("MEMBER_001", "존재하지 않는 사용자입니다.", HttpStatus.CONFLICT),
    UN_REGISTERED_MEMBER("MEMBER_002", "", HttpStatus.OK),
    DUPLICATE_MEMBER("MEMBER_003", "중복된 사용자입니다.", HttpStatus.CONFLICT),
    DUPLICATE_NICKNAME("MEMBER_004", "중복된 닉네임입니다.", HttpStatus.CONFLICT),
    INVALID_MEMBER("MEMBER_005", "올바르지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
