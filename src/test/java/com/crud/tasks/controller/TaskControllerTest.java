package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasksShouldReturnListOfTaskDto() {
        // given
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        when(dbService.getAllTasks()).thenReturn(Arrays.asList(task));
        when(taskMapper.mapToTaskDtoList(Arrays.asList(task))).thenReturn(Arrays.asList(taskDto));

        // when
        ResponseEntity<List<TaskDto>> responseEntity = taskController.getTasks();

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(taskDto, responseEntity.getBody().get(0));
    }

    @Test
    void getTaskShouldReturnTaskDto() throws TaskNotFoundException {
        // given
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // when
        ResponseEntity<TaskDto> responseEntity = taskController.getTask(1L);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(taskDto, responseEntity.getBody());
    }

    @Test
    void getTaskShouldThrowTaskNotFoundException() throws TaskNotFoundException {
        // given
        when(dbService.getTask(1L)).thenThrow(TaskNotFoundException.class);

        // when & then
        assertThrows(TaskNotFoundException.class, () -> taskController.getTask(1L));
    }

    @Test
    void deleteTaskShouldReturnNoContent() throws TaskNotFoundException {
        // given
        Long taskId = 1L;
        doNothing().when(dbService).deleteTask(taskId);

        // when
        ResponseEntity<Void> responseEntity = taskController.deleteTask(taskId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteTaskShouldReturnNotFound() throws TaskNotFoundException {
        // given
        Long taskId = 1L;
        doThrow(TaskNotFoundException.class).when(dbService).deleteTask(taskId);

        // when
        ResponseEntity<Void> responseEntity = taskController.deleteTask(taskId);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void updateTaskShouldReturnUpdatedTaskDto() {
        // given
        TaskDto taskDto = new TaskDto(1L, "Updated task", "Updated content");
        Task task = new Task(1L, "Updated task", "Updated content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // when
        ResponseEntity<TaskDto> responseEntity = taskController.updateTask(taskDto);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(taskDto, responseEntity.getBody());
    }

    @Test
    void createTaskShouldReturnCreatedTask() {
        // given
        TaskDto taskDto = new TaskDto(1L, "New task", "New content");
        Task task = new Task(1L, "New task", "New content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        // when
        ResponseEntity<Void> responseEntity = taskController.createTask(taskDto);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
