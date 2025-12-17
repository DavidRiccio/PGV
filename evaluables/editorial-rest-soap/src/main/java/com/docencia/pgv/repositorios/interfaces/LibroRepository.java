package com.docencia.pgv.repositorios.interfaces;

import java.util.List;
import java.util.Optional;

import com.docencia.pgv.modelo.Libro;


public interface LibroRepository {

    /**
     * Lista todos los libros
     * @return lista con todos los libros
     */
    List<Libro> findAll();

    /**
     * Busca un Libro por id
     * @param id id del libro
     * @return el libro o null
     */
    Optional<Libro> findById(Long id);

    /**
     * Busca los libros de un autor
     * @param idAutor id del autor
     * @return Lista de libros
     */
    List<Libro> findByIdAutor(Long idAutor);

    /**
     * Guarda un libro
     * @param libro libro a guardar
     * @return El libro
     */
    Libro save(Libro libro);

    /**
     * Borra un libro por id
     * @param id id del libro
     * @return true si lo borra
     */
    boolean deleteById(Long id);

    /**
     * Borra todos los libros
     */
    void deleteAll();
}
