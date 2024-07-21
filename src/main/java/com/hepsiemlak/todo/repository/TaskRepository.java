package com.hepsiemlak.todo.repository;


import com.hepsiemlak.todo.model.Task;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends CouchbaseRepository<Task, String> {
    List<Task> findByUsername(String username);
}
