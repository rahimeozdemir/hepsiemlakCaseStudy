package com.hepsiemlak.todo.model.dto;

import com.hepsiemlak.todo.enums.Status;
import com.hepsiemlak.todo.model.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private Status status;
}
