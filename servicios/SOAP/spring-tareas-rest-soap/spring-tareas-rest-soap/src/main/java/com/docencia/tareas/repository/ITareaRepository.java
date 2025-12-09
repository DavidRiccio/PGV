package com.docencia.tareas.repository;

import java.util.List;

import com.docencia.tareas.model.Tarea;

public interface ITareaRepository {
    /**
     * Añade una tarea
     * 
     * @param tarea tarea a añadir
     * @return la tarea añadida
     */
    public Tarea add(Tarea tarea);

    /**
     * Borra una tarea
     * 
     * @param tarea tarea que quieres borrar
     * @return true si borra
     */
    public boolean delete(Tarea tarea);

    /**
     * Busca una tarea
     * 
     * @param tarea tarea que busca
     * @return la tarea que encuentre
     */
    public Tarea findBy(Tarea tarea);

    /**
     * Actualiza una tarea
     * 
     * @param tarea tarea a actualizar
     * @return la tarea actualizada
     */
    public Tarea update(Tarea tarea);

    /**
     * Lista todas las tareas
     * 
     * @return todas las tareas
     */
    public List<Tarea> all();

}
