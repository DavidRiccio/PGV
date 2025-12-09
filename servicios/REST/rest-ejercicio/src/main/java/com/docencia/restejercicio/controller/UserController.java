package com.docencia.restejercicio.controller;

import com.docencia.restejercicio.model.User;
import com.docencia.restejercicio.service.UserService;
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
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserRepository(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @Operation(summary = "Obtener usuario por id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {

        if (userId == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.getById(userId);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Crear nuevo usuario")
    @PostMapping("/")
    public User craetUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @Operation(summary = "Actualizar un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
            @Valid @RequestBody User userDetails) {
        if (userId == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.update(userId, userDetails);
        return ResponseEntity.ok().body(user);

    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) {
        userService.delete(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
