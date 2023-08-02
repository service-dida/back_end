package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NftErrorCode implements ErrorCode {
    EMPTY_NFT("NFT_001", "유효하지 않는 NFT입니다.", HttpStatus.CONFLICT),
    FAILED_CREATE_METADATA("NFT_002", "메타데이터 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_CREATE_NFT("NFT_003", "NFT 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_SEND_NFT("NFT_004", "NFT 전송에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_DRAW_PICTURE("NFT_005","",HttpStatus.BAD_REQUEST),
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
