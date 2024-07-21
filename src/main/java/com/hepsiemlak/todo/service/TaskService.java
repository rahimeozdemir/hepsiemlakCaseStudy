package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.mapper.TaskMapper;
import com.hepsiemlak.todo.model.Task;
import com.hepsiemlak.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task get(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
    }

    public Task save(Task task) {
        var savedTask = taskRepository.save(task);
        log.info("Task with id {} was inserted successfully", task.getId());
        return savedTask;
    }

    public List<Task> findByUserUsername(String username) {
        return taskRepository.findByUsername(username);
    }

    public void update(Task newTask) {
        var existedTask = taskRepository.findById(newTask.getId())
                .orElseThrow(()-> new NotFoundException("Task not found"));

        var updatedTask = TaskMapper.INSTANCE.existedTaskToUpdatedTask(newTask, existedTask);
        taskRepository.save(updatedTask);
        log.info("Task with id {} was updated successfully", updatedTask.getId());
    }

    public void delete(String taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException("Task not found");
        }
        taskRepository.deleteById(taskId);
        log.info("Task with id {} was deleted", taskId);
    }
}
