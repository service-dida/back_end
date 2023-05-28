package com.service.dida.global.config.exception.errorCode;

import com.service.dida.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    EMPTY_COMMENT("COMMENT_001", "유효하지 않은 댓글입니다.", HttpStatus.CONFLICT),
    NOT_OWN_COMMENT("COMMENT_002", "자신의 댓글이 아닙니다.",HttpStatus.CONFLICT)
    ;
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
