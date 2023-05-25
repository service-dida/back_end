package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NftErrorCode implements ErrorCode {
    EMPTY_NFT("NFT_001", "유효하지 않는 NFT입니다.",HttpStatus.CONFLICT);
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
