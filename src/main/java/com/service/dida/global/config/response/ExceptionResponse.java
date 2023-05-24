package com.service.dida.global.config.response;

import com.service.dida.global.config.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;

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
