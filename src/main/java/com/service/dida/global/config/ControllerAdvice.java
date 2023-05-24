package com.service.dida.global.config;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.ErrorCode;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import com.service.dida.global.config.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ExceptionResponse(e.getErrorCode(),
            e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        System.out.println(e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getErrorCode(),
                e.getMessage());
        return exceptionResponse;
    }

    private String extractMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach((error) -> builder.append(error.getDefaultMessage()));
        return builder.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ExceptionResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse handleMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ExceptionResponse handleMediaTypeNotSupportedException(
        HttpMediaTypeNotSupportedException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse handleRuntimeException(RuntimeException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.SERVER_ERROR);
    }

    private ExceptionResponse convert(ErrorCode e) {
        return new ExceptionResponse(e.getErrorCode(), e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> convert(ErrorCode e, String detailMessage) {
        ExceptionResponse exceptionRes = new ExceptionResponse(e.getErrorCode(), detailMessage);
        return new ResponseEntity<>(
            exceptionRes, HttpStatus.BAD_REQUEST);
    }
    
}
