package com.docencia.pgv.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.docencia.pgv.modelo.Libro;
import com.docencia.pgv.servicios.LibroServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/libros")
@Tag(name ="Libros" )
public class LibroRestController {
    private final LibroServiceImpl libroServiceImpl;

    public LibroRestController(LibroServiceImpl libroServiceImpl) {
        this.libroServiceImpl = libroServiceImpl;
    }

    @Operation(summary = "Lista todos los libros")
    @GetMapping("/")
    public ResponseEntity<List<Libro>> listar() {
        List<Libro> books= libroServiceImpl.findAll();
        return ResponseEntity.ok().body(books);
    }

    @Operation(summary = "Busca un libro por id")
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getProductoById(@PathVariable(value = "id") Long id) {
        Libro libro = libroServiceImpl.findByIdOrThrow(id);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(libro);
    }

    @Operation(summary = "Busca los libros de un autor")
    @GetMapping("/autor/{id}")
    public ResponseEntity<List<Libro>> getLibroByAutorId(Long id){
        if (id == null){
            return ResponseEntity.notFound().build();
        }
        List<Libro> libros = libroServiceImpl.findByAutorOrThrow(id);
        return ResponseEntity.ok().body(libros);
    }

    @Operation(summary = "Crea un libro")
    @PostMapping("/")
    public Libro createAutor(Libro libro) {
        return libroServiceImpl.create(libro);
    }

    @Operation(summary = "Borra un libro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLibro(Long id) {
        Libro libro = libroServiceImpl.findByIdOrThrow(id);
        if (libro == null){
            return ResponseEntity.notFound().build();
        }
        libroServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }

}
