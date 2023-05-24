package com.service.dida.global.config;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.ErrorCode;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import com.service.dida.global.config.response.ExceptionResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BaseException.class)
    public ExceptionResponse handleBaseException(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getErrorCode(),
                e.getMessage());
        return exceptionResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleException(MethodArgumentNotValidException e) {
        String detailMessage = extractMessage(e.getBindingResult().getFieldErrors());
        return convert(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR, detailMessage);
    }

    private String extractMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach((error) -> {
            builder.append("[");
            builder.append(error.getField());
            builder.append(": ");
            builder.append(error.getDefaultMessage());
            builder.append(" || input value: ");
            builder.append("]");
            builder.append(System.lineSeparator());
        });
        return builder.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ExceptionResponse handleNoHandlerFoundException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse handleMethodNotSupportedException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ExceptionResponse handleMediaTypeNotSupportedException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse handleRuntimeException() {
        return convert(GlobalErrorCode.SERVER_ERROR);
    }

    private ExceptionResponse convert(ErrorCode e) {
        ExceptionResponse exceptionRes = new ExceptionResponse(e.getErrorCode(), e.getMessage());
        return exceptionRes;
    }

    private ExceptionResponse convert(ErrorCode e, String detailMessage) {
        ExceptionResponse exceptionRes = new ExceptionResponse(e.getErrorCode(), detailMessage);
        return exceptionRes;
    }
}
