package com.docencia.pgv.interfaces;

import java.util.List;
import com.docencia.pgv.modelo.Autor;

public interface AutorService {
    /**
     * Lista todos los autores
     * 
     * @return Lista de autores
     */
    List<Autor> findAll();

    /**
     * Busca un autor por id
     * 
     * @param id id del autor
     * @return Autor buscado
     */
    Autor findByIdOrThrow(Long id);

    /**
     * Crea un autor
     * 
     * @param autor autor a crear
     * @return autor creado
     */
    Autor create(Autor autor);

    /**
     * Borra un autor a traves de su id
     * 
     * @param id id del autor
     */
    void delete(Long id);
}
