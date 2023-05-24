package com.service.dida.global.config.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;
    private String message;

    public BaseException(ErrorCode code) {
        errorCode = code.getErrorCode();
        message = code.getMessage();
    }

    public void setEmailMessage(String message) {
        this.message = "해당 사용자의 이메일은 " + message + " 입니다.";
    }
}
