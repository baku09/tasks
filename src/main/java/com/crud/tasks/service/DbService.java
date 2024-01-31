package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public Task getTask(final Long taskId) throws TaskNotFoundException {
        return repository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }
    public void deleteTask(final Long taskId) throws TaskNotFoundException {
        if (repository.existsById(taskId)) {
            repository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException();
        }
    }
}
