package com.service.dida.global.config;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.ErrorCode;
import com.service.dida.global.config.exception.errorCode.GlobalErrorCode;
import com.service.dida.global.config.response.ExceptionResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getErrorCode(),
            e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        System.out.println(e);
        String detailMessage = extractMessage(e.getBindingResult().getFieldErrors());
        return convert(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR, detailMessage);
    }

    private String extractMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach((error) -> builder.append(error.getDefaultMessage()));
        return builder.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(
        NoHandlerFoundException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD_ERROR, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMediaTypeNotSupportedException(
        HttpMediaTypeNotSupportedException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR,
            HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        System.out.println(e);
        return convert(GlobalErrorCode.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> convert(ErrorCode e, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ExceptionResponse(e.getErrorCode(), e.getMessage()),
            httpStatus);
    }

    private ResponseEntity<ExceptionResponse> convert(ErrorCode e, String detailMessage) {
        ExceptionResponse exceptionRes = new ExceptionResponse(e.getErrorCode(), detailMessage);
        return new ResponseEntity<>(
            exceptionRes, HttpStatus.BAD_REQUEST);
    }

}
