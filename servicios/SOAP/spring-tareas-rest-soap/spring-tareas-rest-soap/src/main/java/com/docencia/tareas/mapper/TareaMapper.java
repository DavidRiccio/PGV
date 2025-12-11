package com.docencia.tareas.mapper;

import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.docencia.tareas.model.Alumno;
import com.docencia.tareas.model.Tarea;

@Mapper
public interface TareaMapper {

    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);

    /**
     * Transforma de un Objeto Tarea a un Objeto Alumno
     * 
     * @param tarea tarea que quieres convertir
     * @return Alumno creado a partir de tarea
     */
    @Mappings({
            @Mapping(source = "id", target = "identificador"),
            @Mapping(source = "titulo", target = "nombre")
    })
    Alumno toAlumno(Tarea tarea);

    /**
     * Transforma una lista de tareas a una lista de alumnos usando el mapper toAlumno
     * @param tareas tareas que quieres convertir a alumnos 
     * @return lista de Alumnos
     */
    List<Alumno> toAlumnos(List<Tarea> tareas);



}
