package es.david.practica.soap.mapper;

import es.david.practica.soap.dto.EstudianteDTO;
import es.david.practica.soap.models.Estudiante;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T22:48:45+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Ubuntu)"
)
public class EstudianteMapperImpl implements EstudianteMapper {

    @Override
    public EstudianteDTO toDTO(Estudiante estudiante) {
        if ( estudiante == null ) {
            return null;
        }

        EstudianteDTO estudianteDTO = new EstudianteDTO();

        estudianteDTO.setIdentificador( estudiante.getId() );
        estudianteDTO.setNombreCompleto( estudiante.getNombre() );
        estudianteDTO.setEmail( estudiante.getEmail() );

        return estudianteDTO;
    }

    @Override
    public List<EstudianteDTO> toDTOs(List<Estudiante> estudiantes) {
        if ( estudiantes == null ) {
            return null;
        }

        List<EstudianteDTO> list = new ArrayList<EstudianteDTO>( estudiantes.size() );
        for ( Estudiante estudiante : estudiantes ) {
            list.add( toDTO( estudiante ) );
        }

        return list;
    }
}
