package com.service.dida.global.config.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getErrorCode();

    String getMessage();

    HttpStatus getStatus();
}
