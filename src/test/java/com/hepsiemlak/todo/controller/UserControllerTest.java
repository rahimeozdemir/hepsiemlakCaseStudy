package com.hepsiemlak.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hepsiemlak.todo.model.Task;
import com.hepsiemlak.todo.model.dto.TaskDto;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.security.JwtService;
import com.hepsiemlak.todo.service.LoginService;
import com.hepsiemlak.todo.service.TaskService;
import com.hepsiemlak.todo.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    void registerUser() {
        // given:
        doNothing().when(userService).save(any());
        UserDto dto = new UserDto();
        dto.setUsername("username");
        dto.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("username", "password"));

        // when:
        this.mockMvc.perform(post("/register")
                        .content(requestJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }

    @Test
    @SneakyThrows
    void login() {
        // given:
        UserDto dto = new UserDto();
        dto.setUsername("username");
        dto.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        when(loginService.login(dto)).thenReturn(anyString());

        // when:
        this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }
}