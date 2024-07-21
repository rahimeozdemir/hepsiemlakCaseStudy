package com.hepsiemlak.todo.model;

import com.hepsiemlak.todo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
@Scope("task")
@Collection("task")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String username;
    private Date dueDate;
    private Status status;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;
}
