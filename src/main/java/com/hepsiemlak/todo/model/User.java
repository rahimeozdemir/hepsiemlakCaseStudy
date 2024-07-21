package com.hepsiemlak.todo.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
@Scope("user")
@Collection("user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
}
