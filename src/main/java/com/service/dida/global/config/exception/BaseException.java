package com.service.dida.global.config.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;
    private String message;
    private final HttpStatus status;

    public BaseException(ErrorCode code) {
        errorCode = code.getErrorCode();
        message = code.getMessage();
        status = code.getStatus();
        log.error("In BaseException ErrorCode: {}, ErrorMessage: {}, Status: {}", errorCode, message, status);
    }

    public void setAiErrorMessage(String message) {
        this.message = message;
    }

    public void setEmailMessage(String message) {
        this.message = message;
    }
}
