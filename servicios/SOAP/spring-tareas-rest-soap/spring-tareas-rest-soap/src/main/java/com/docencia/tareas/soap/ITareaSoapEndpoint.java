package com.docencia.tareas.soap;

import java.util.List;

import com.docencia.tareas.model.Tarea;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ies.puerto.es/ws/tarea", name = "TareaPortType")
public interface ITareaSoapEndpoint {

    /**
     * Lista todas las tareas
     * 
     * @return Lista de tareas
     */

    @WebMethod(operationName = "listarAll")
    List<Tarea> listar();

    /**
     * Busca una tarea por id
     * 
     * @param id id de la tarea
     * @return la tarea
     */

    @WebMethod(operationName = "buscar")
    Tarea buscar(@WebParam(name = "identificador") Long id);

    /**
     * Crea una tarea
     * 
     * @param titulo      titulo de la tarea
     * @param descripcion descripcion de la tarea
     * @return la tarea que has creado
     */

    @WebMethod(operationName = "crear")
    Tarea crear(String titulo, String descripcion);

    /**
     * Actualiza una tarea
     * 
     * @param id          id de la tarea
     * @param titulo      titulo de la tarea
     * @param descripcion descripcion de la tarea
     * @param completada  estado de la tarea
     * @return la tarea actualizada
     */

    @WebMethod(operationName = "actualizar")
    Tarea actualizar(Long id, String titulo, String descripcion, Boolean completada);

    /**
     * Elimina una tarea a traves de su id
     * 
     * @param id
     * @return
     */

    @WebMethod(operationName = "eliminar")
    boolean eliminar(Long id);

}
