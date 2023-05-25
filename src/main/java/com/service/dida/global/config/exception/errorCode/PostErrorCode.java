package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {
    EMPTY_POST("POST_001", "유효하지 않은 게시글입니다.",HttpStatus.CONFLICT),
    NOT_OWN_POST("POST_002", "자신의 게시글이 아닙니다.",HttpStatus.CONFLICT)
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
