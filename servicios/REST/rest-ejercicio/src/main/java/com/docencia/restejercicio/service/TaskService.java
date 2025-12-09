package com.docencia.restejercicio.service;

import com.docencia.restejercicio.model.Task;
import com.docencia.restejercicio.repository.TaskRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository repository;
    
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Task create(Task task) {
        if (task.getId()== null){
            task.setId((long)repository.findAll().size());
        }
        return repository.save(task);
    }

    public Task update(Long id, Task update) {
        Task task = update;
        task.setId(id);
        repository.save(task);
        return task;

    }

    public void delete(Long id) {
        repository.deleteById(id);

    }
}
