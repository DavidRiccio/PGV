package com.docencia.pgv.repositorios.interfaces;

import java.util.List;
import java.util.Optional;

import com.docencia.pgv.modelo.Autor;

public interface AutorRepository {
    /**
     * Busca todos los autores
     * 
     * @return Lista de autores
     */
    List<Autor> findAll();

    /**
     * Busca un autor por id
     * 
     * @param id id del autor
     * @return El autor o null
     */
    Optional<Autor> findById(Long id);

    /**
     * Guarda un autor
     * 
     * @param autor autor a guardar
     * @return El autor guardado
     */
    Autor save(Autor autor);

    /**
     * Borra un autor por id
     * 
     * @param id id del autor
     * @return true si lo borra
     */
    boolean deleteById(Long id);

    /**
     * Borra todos los autores
     */
    void deleteAll();
}
