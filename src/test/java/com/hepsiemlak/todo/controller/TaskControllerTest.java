package com.hepsiemlak.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hepsiemlak.todo.model.Task;
import com.hepsiemlak.todo.model.dto.TaskDto;
import com.hepsiemlak.todo.security.JwtService;
import com.hepsiemlak.todo.service.TaskService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private JwtService jwtService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @SneakyThrows
    void listTasks() {
        // given:
        var task = new Task();
        when(taskService.findByUserUsername(anyString())).thenReturn(List.of(task));
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // when:
        this.mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }

    @Test
    @SneakyThrows
    void getTask() {
        // given:
        var task = new Task();
        when(taskService.get(anyString())).thenReturn(task);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // when:
        this.mockMvc.perform(get("/tasks")
                        .param("taskId", "1"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }

    @Test
    @SneakyThrows
    void add() {
        // given:
        var taskDto = new TaskDto();
        var task = new Task();
        when(taskService.save(any())).thenReturn(task);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(taskDto);

        // when:
        this.mockMvc.perform(post("/tasks")
                        .content(requestJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }

    @Test
    @SneakyThrows
    void update() {
        // given:
        var task = new TaskDto();
        doNothing().when(taskService).update(any());
        when(securityContext.getAuthentication()).thenReturn(authentication);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(task);

        // when:
        this.mockMvc.perform(put("/tasks")
                        .content(requestJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }


    @Test
    @SneakyThrows
    void it_should_delete() {
        // given:
        doNothing().when(taskService).delete(anyString());
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // when:
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }
}