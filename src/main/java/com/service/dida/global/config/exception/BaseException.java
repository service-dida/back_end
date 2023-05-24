package com.service.dida.global.config.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public BaseException(ErrorCode code) {
        errorCode = code.getErrorCode();
        message = code.getMessage();
    }
}
