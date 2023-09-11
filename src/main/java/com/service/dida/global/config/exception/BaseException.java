package com.service.dida.global.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;
    private String message;
    private final HttpStatus status;

    public BaseException(ErrorCode code) {
        errorCode = code.getErrorCode();
        message = code.getMessage();
        status = code.getStatus();
    }

    public void setAiErrorMessage(String message) {
        this.message = message;
    }

    public void setEmailMessage(String message) {
        this.message = message;
    }
}
