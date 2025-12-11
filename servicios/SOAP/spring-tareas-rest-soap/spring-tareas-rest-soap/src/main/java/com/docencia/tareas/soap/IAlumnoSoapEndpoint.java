package com.docencia.tareas.soap;

import java.util.List;

import com.docencia.tareas.model.Alumno;

import jakarta.jws.*;

@WebService(targetNamespace = "http://ies.puerto.es/ws/alumno", name = "AlumnoPortType")

public interface IAlumnoSoapEndpoint {
      /**
     * Lista todas las tareas
     * 
     * @return Lista de tareas
     */

    @WebMethod(operationName = "listar")
    List<Alumno> listar();
}
