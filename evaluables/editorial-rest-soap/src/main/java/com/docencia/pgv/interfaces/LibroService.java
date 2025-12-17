package com.docencia.pgv.interfaces;

import java.util.List;
import com.docencia.pgv.modelo.Libro;

public interface LibroService {
    /**
     * Lista todos los libros
     * 
     * @return Lista de libros
     */
    List<Libro> findAll();

    /**
     * Busca un libro por id
     * 
     * @param id id del libro
     * @return libro buscado
     */
    Libro findByIdOrThrow(Long id);

    /**
     * Lista los libros de un autor
     * 
     * @param idAutor id del autor
     * @return libros del autor
     */
    List<Libro> findByAutorOrThrow(Long idAutor);

    /**
     * Crea un libro
     * 
     * @param libro libro a crear
     * @return libro creado
     */
    Libro create(Libro libro);

    /**
     * Borra un libro a traves de su id
     * 
     * @param id id del libro
     */
    void delete(Long id);
}
