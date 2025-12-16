package com.docencia.tareas.service;

import java.util.List;

import com.docencia.tareas.model.Alumno;

public interface IAlumnoService {
    /**
     * Lista todas los alumnos
     * @return
     */
    List<Alumno> listarTodas();
}
