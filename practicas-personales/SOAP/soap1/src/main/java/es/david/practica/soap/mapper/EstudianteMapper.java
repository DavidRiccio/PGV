package es.david.practica.soap.mapper;

import java.util.List;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import es.david.practica.soap.dto.EstudianteDTO;
import es.david.practica.soap.models.Estudiante;

@Mapper
public interface EstudianteMapper {
    
    EstudianteMapper INSTANCE = Mappers.getMapper(EstudianteMapper.class);
    
    /**
     * Transforma de Estudiante a EstudianteDTO
     * 
     * @param estudiante estudiante que quieres convertir
     * @return EstudianteDTO creado a partir del estudiante
     */
    @Mappings({
        @Mapping(source = "id", target = "identificador"),
        @Mapping(source = "nombre", target = "nombreCompleto")
    })
    EstudianteDTO toDTO(Estudiante estudiante);
    
    /**
     * Transforma una lista de estudiantes a una lista de DTOs
     * 
     * @param estudiantes lista de estudiantes
     * @return lista de EstudianteDTO
     */
    List<EstudianteDTO> toDTOs(List<Estudiante> estudiantes);
}
