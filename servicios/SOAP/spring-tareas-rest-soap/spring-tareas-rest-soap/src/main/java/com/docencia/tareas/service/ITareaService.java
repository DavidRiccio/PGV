package com.docencia.tareas.service;

import java.util.List;

import com.docencia.tareas.model.Tarea;

public interface ITareaService {
    /**
     * Lista todas las tareas
     * 
     * @return
     */
    List<Tarea> listarTodas();

    /**
     * Busca una tarea por id
     * 
     * @param id id de la tarea
     * @return la tarea
     */
    Tarea buscarPorId(Long id);

    /**
     * Crea una tarea
     * 
     * @param titulo      titulo de la tarea
     * @param descripcion descripcion de la tarea
     * @return la tarea que has creado
     */
    Tarea crearTarea(String titulo, String descripcion);

    /**
     * Actualiza una tarea
     * 
     * @param id          id de la tarea
     * @param titulo      titulo de la tarea
     * @param descripcion descripcion de la tarea
     * @param completada  estado de la tarea
     * @return la tarea actualizada
     */
    Tarea actualizarTarea(Long id, String titulo, String descripcion, Boolean completada);

    /**
     * 
     * @param id
     * @return
     */
    boolean eliminarTarea(Long id);

}
