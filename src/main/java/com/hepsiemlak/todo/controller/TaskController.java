package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.mapper.TaskMapper;
import com.hepsiemlak.todo.model.dto.BaseResponseDto;
import com.hepsiemlak.todo.model.dto.TaskDto;
import com.hepsiemlak.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<BaseResponseDto<List<TaskDto>>> listTasks() {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        var tasks = taskService.findByUserUsername(currentUserName);

        var dtoList = TaskMapper.INSTANCE.tasksToTaskDtoList(tasks);

        return ResponseEntity.ok(BaseResponseDto.<List<TaskDto>>builder()
                .data(dtoList)
                .build());
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<BaseResponseDto<TaskDto>> getTask(@RequestParam("id") String id) {
        var task = taskService.get(id);

        var taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);

        return ResponseEntity.ok(BaseResponseDto.<TaskDto>builder()
                .data(taskDto)
                .build());
    }

    @PostMapping("/tasks")
    public ResponseEntity<BaseResponseDto<TaskDto>> add(@RequestBody TaskDto dto) {
        var task = TaskMapper.INSTANCE.taskDtoToTask(dto);

        task.setId(UUID.randomUUID().toString());

        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setUsername(currentUserName);

        var savedTask = taskService.save(task);
        var savedTaskDto = TaskMapper.INSTANCE.taskToTaskDto(savedTask);

        log.info("Task added successfully: {}", savedTaskDto);

        return ResponseEntity.ok(BaseResponseDto.<TaskDto>builder()
                .data(savedTaskDto)
                .message("Task added successfully")
                .build());
    }

    @PutMapping("/tasks")
    public ResponseEntity<BaseResponseDto<Object>> update(@RequestBody TaskDto dto) {
        var task = TaskMapper.INSTANCE.taskDtoToTask(dto);
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setUsername(currentUserName);

        taskService.update(task);
        log.info("Task updated successfully");

        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("Task updated successfully")
                .build());
    }

    @DeleteMapping(value = "/tasks/{taskId}")
    public ResponseEntity<BaseResponseDto<Object>> delete(@PathVariable String taskId) {
        taskService.delete(taskId);
        log.info("Task deleted successfully");

        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("Task deleted successfully")
                .build());
    }
}
