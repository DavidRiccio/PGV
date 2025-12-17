package com.docencia.pgv.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.docencia.pgv.modelo.Autor;
import com.docencia.pgv.servicios.AutorServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autores")
public class AutorRestController {

    private final AutorServiceImpl autorServiceImpl;

    public AutorRestController(AutorServiceImpl autorServiceImpl) {
        this.autorServiceImpl = autorServiceImpl;
    }
    
    @Operation(summary = "Lista todos los autores")
    @GetMapping("/")
    public ResponseEntity<List<Autor>> listar() {
        List<Autor> autors= autorServiceImpl.findAll();
        return ResponseEntity.ok().body(autors);
    }

    @Operation(summary = "Busca un autor por id")
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getProductoById(@PathVariable(value = "id") Long id) {
        Autor autor = autorServiceImpl.findByIdOrThrow(id);
        if (autor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(autor);
    }

    @Operation(summary = "Crea un autor")
    @PostMapping("/")
    public Autor createAutor( String nombre, String pais) {
        Autor autor = new Autor(nombre, pais);
        return autorServiceImpl.create(autor);
    }

    @Operation(summary = "Borra un autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAutor(Long id) {
        Autor autor = autorServiceImpl.findByIdOrThrow(id);
        if (autor == null) {
            return ResponseEntity.notFound().build();
        }
        autorServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
        
    }


