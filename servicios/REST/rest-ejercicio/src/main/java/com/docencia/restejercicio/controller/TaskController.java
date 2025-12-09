package com.docencia.restejercicio.controller;

import com.docencia.restejercicio.model.Task;
import com.docencia.restejercicio.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    
   @Operation(summary = "Obtener todas las tareas")
    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    @Operation(summary = "Obtener tarea por id")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId) {
        if (taskId == null){
            return ResponseEntity.notFound().build();
        }
        Task task = taskService.getById(taskId);
        return ResponseEntity.ok().body(task);

    }

    @Operation(summary = "Crear nueva tarea")
    @PostMapping("/")
    public Task craetTask(@Valid @RequestBody Task task) {
        return taskService.create(task);
    }

    @Operation(summary = "Actualizar una tarea")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId,
            @Valid @RequestBody Task taskDetails) {
                Task task = taskService.update(taskId,taskDetails);
        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "Eliminar una tarea")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId){

        taskService.delete(taskId); 
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;    }

}
