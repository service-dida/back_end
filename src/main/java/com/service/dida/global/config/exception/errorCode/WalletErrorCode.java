package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WalletErrorCode implements ErrorCode {
    EMPTY_WALLET("WALLET_001", "지갑이 없습니다.", HttpStatus.FORBIDDEN),
    WRONG_PWD("WALLET_002", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    FAILED_CREATE_WALLET("WALLET_003", "지갑 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
