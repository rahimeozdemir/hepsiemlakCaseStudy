package com.hepsiemlak.todo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class BaseResponseDto<T> {
    private String message;
    private T data;
    private ErrorMessageDto error;
}
