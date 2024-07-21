package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.UserNotLoginException;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.repository.UserRepository;
import com.hepsiemlak.todo.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository repository;

    @Test
    void save() {
        // given:
        var user = new User();
        user.setUsername("username");
        user.setPassword("password");

        // when:
        sut.save(user);

        // then:
        verify(repository).save(any());
    }

    @Test
    void it_should_throws_an_exception_when_user_not_found() {
        // given:
        when(repository.findByUsername(any())).thenReturn(Optional.empty());

        // when:
        assertThrows(UsernameNotFoundException.class, () -> sut.loadUserByUsername(any()));

        // then:
    }

    @Test
    void it_should_get_user_by_username() {
        // given:
        when(repository.findByUsername(any())).thenReturn(
                Optional.of(User.builder()
                        .username("username")
                        .password("password")
                        .build()));

        // when:
        var actual = sut.loadUserByUsername(any());

        // then:
        assertNotNull(actual);
    }
}