package com.hepsiemlak.todo.exception;

import com.hepsiemlak.todo.model.dto.BaseResponseDto;
import com.hepsiemlak.todo.model.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto<Object>> handleGlobalExceptions(Exception e) {
        ErrorMessageDto message = new ErrorMessageDto(e.getMessage());
        var baseResponseDto = BaseResponseDto.builder()
                .error(message)
                .build();
        return new ResponseEntity<>(baseResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}