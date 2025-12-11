package com.docencia.tareas.soap;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.tareas.model.Alumno;
import com.docencia.tareas.service.IAlumnoService;

import jakarta.jws.*;

@WebService(serviceName = "AlumnoService", portName = "AlumnoPort", targetNamespace = "http://ies.puerto.es/ws/alumno", endpointInterface = "com.docencia.tareas.soap.IAlumnoSoapEndpoint")
@Service
public class AlumnoSoapService implements IAlumnoSoapEndpoint {

    private final  IAlumnoService alumnoService;

    public AlumnoSoapService(IAlumnoService alumnoService){
        this.alumnoService = alumnoService;

    }


    @Override
    public List<Alumno> listar() {
       return alumnoService.listarTodas();
    }

}
