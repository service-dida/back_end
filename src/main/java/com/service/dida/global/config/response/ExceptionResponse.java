package com.service.dida.global.config.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
