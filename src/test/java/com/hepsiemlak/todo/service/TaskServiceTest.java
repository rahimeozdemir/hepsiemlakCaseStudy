package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.model.Task;
import com.hepsiemlak.todo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService sut;

    @Mock
    private TaskRepository repository;


    @Test
    void it_should_thrown_exception_when_task_not_found() {
        // given:
        when(repository.findById(any())).thenThrow(new RuntimeException());

        // when:
        assertThrows(RuntimeException.class, () -> sut.get(any()));

        // then:
    }

    @Test
    void it_should_get_successfully() {
        // given:
        when(repository.findById(any())).thenReturn(Optional.of(new Task()));

        // when:
        var actual = sut.get(any());

        // then:
        assertNotNull(actual);
    }

    @Test
    void it_should_save_successfully() {
        // given:
        var task = new Task();
        task.setId("1");
        when(repository.save(any())).thenReturn(task);

        // when:
        assertDoesNotThrow(() -> sut.save(task));

        // then:
    }

    @Test
    void it_should_find_task_list_user_by_username() {
        // given:
        when(repository.findByUsername(any())).thenReturn(List.of(new Task()));

        // when:
        var actual = sut.findByUserUsername(any());

        // then:
        assertEquals(1, actual.size());
    }


    @Test
    void it_should_update_successfully() {
        // given:
        var task = new Task();
        task.setId("1");
        when(repository.findById(any())).thenReturn(Optional.of(new Task()));
        when(repository.save(any())).thenReturn(new Task());

        // when:
        assertDoesNotThrow(() -> sut.update(task));

        // then:
    }

    @Test
    void it_should_not_delete_and_thrown_exception_when_task_not_found() {
        // given:
        when(repository.existsById(any())).thenReturn(false);

        // when:
        assertThrows(NotFoundException.class, () -> sut.delete(any()));

        // then:
        verify(repository, never()).deleteById(any());
    }

    @Test
    void it_should_delete_successfully() {
        // given:
        when(repository.existsById(any())).thenReturn(true);

        // when:
        sut.delete(any());

        // then:
    }
}