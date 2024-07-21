package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.UserNotLoginException;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(UserDto dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(dto.getUsername());
        } else {
            throw new UserNotLoginException("User not login");
        }
    }
}
