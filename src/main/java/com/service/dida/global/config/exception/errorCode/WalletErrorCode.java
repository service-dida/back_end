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
    FAILED_USE_WALLET("WALLET_004", "3분간은 지갑을 이용할 수 없습니다.", HttpStatus.CONFLICT),
    IN_MEMBER_ADDRESS("WALLET_005", "디다 사용자의 주소입니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
