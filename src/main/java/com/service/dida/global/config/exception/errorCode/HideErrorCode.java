package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HideErrorCode implements ErrorCode {
    ALREADY_HIDE("HIDE_001", "이미 숨긴 NFT입니다.", HttpStatus.CONFLICT),
    SELF_HIDE("HIDE_002", "자신의 NFT는 숨길 수 없습니다.", HttpStatus.CONFLICT),
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
