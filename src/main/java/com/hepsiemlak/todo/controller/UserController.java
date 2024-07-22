package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.mapper.UserMapper;
import com.hepsiemlak.todo.model.dto.BaseResponseDto;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.service.LoginService;
import com.hepsiemlak.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("register")
    public ResponseEntity<BaseResponseDto<String>> registerUser(@RequestBody UserDto dto) {
        var user = UserMapper.INSTANCE.userDtoToUser(dto);
        userService.save(user);
        return ResponseEntity.ok(BaseResponseDto.<String>builder()
                .message("User registered successfully")
                .build());
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponseDto<String>> login(@RequestBody UserDto dto) {
        var token = loginService.login(dto);
        return ResponseEntity.ok(BaseResponseDto.<String>builder()
                .data(token)
                .message("Login successful")
                .build());
    }
}
