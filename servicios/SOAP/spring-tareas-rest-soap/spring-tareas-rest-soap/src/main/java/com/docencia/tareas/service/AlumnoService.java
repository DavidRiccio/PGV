package com.docencia.tareas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.tareas.mapper.TareaMapper;
import com.docencia.tareas.model.Alumno;
import com.docencia.tareas.repository.ITareaRepository;



@Service
public class AlumnoService implements IAlumnoService {
    ITareaRepository tareaRepository;

    public AlumnoService(ITareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Override
    public List<Alumno> listarTodas() {
        return TareaMapper.INSTANCE.toAlumnos(tareaRepository.all());
    }

}
