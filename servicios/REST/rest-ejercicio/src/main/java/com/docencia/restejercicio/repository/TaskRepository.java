package com.docencia.restejercicio.repository;

import com.docencia.restejercicio.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    List<Task> tasks;

    public TaskRepository() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Encuentra todas las tareas
     * 
     * @return Lista de las tareas
     */
    public List<Task> findAll() {
        return tasks;
    }

    /**
     * Busca una tarea por id
     * 
     * @param id id de la tarea
     * @return la tarea o null
     */
    public Optional<Task> findById(Long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            } else {
                return null;
            }

        }

        return null;
    }

    /**
     * Guarda la tarea
     * 
     * @param task tarea que guarda
     * @return la tarea
     */
    public Task save(Task task) {
        Task task1 = new Task(task.getId(), task.getTitle(), task.getDescription(), task.isDone());
        tasks.add(task1);
        return task1;
    }

    /**
     * Borra la tarea a traves de la id
     * 
     * @param id id de la tara
     */
    public void deleteById(Long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
            }
        }
    }

    /**
     * dice si existe una tarea a traves de su id
     * 
     * @param id id de la tarea
     * @return
     */
    public boolean existsById(Long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
