package com.hepsiemlak.todo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
}
