package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    NOT_VALID_ARGUMENT_ERROR("GLOBAL_001", "올바른 argument를 입력해주세요."),
    NOT_SUPPORTED_URI_ERROR("GLOBAL_002", "올바른 URI로 접근해주세요."),
    NOT_SUPPORTED_METHOD_ERROR("GLOBAL_003", "지원하지 않는 Method입니다."),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR("GLOBAL_004", "지원하지 않는 Media type입니다."),
    SERVER_ERROR("GLOBAL_005", "서버와의 연결에 실패하였습니다."),
    ;

    private final String errorCode;
    private final String message;
}
