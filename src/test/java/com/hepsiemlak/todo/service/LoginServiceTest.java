package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.UserNotLoginException;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @InjectMocks
    private LoginService sut;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private Authentication authentication;


    @Test
    void it_should_throw_an_exception() {
        // given:
        UserDto dto = new UserDto();
        dto.setUsername("username");
        dto.setPassword("password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // when:
        assertThrows(UserNotLoginException.class, () -> sut.login(dto));

        // then:
    }

    @Test
    void it_should_login_successfully() {
        // given:
        UserDto dto = new UserDto();
        dto.setUsername("username");
        dto.setPassword("password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        // when:
        sut.login(dto);

        // then:
        verify(jwtService).generateToken("username");
    }
}