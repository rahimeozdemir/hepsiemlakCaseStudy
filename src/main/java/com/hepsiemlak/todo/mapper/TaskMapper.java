package com.hepsiemlak.todo.mapper;

import com.hepsiemlak.todo.model.Task;
import com.hepsiemlak.todo.model.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {TaskMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "username", ignore = true)
    Task taskDtoToTask(TaskDto dto);

    @Mapping(target = "description", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "title", source = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dueDate", source = "dueDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", source = "status", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "username", ignore = true)
    Task existedTaskToUpdatedTask(Task existedTask, @MappingTarget Task newTask);

    TaskDto taskToTaskDto(Task task);

    @Mapping(target = "id", source = "id")
    List<TaskDto> tasksToTaskDtoList(List<Task> tasks);
}