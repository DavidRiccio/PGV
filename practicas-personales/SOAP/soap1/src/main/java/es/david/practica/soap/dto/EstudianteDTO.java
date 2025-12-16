package es.david.practica.soap.dto;

public class EstudianteDTO {
    
    private Long identificador;  // Mapeado desde 'id' de Estudiante
    private String nombreCompleto;  // Mapeado desde 'nombre' de Estudiante
    private String email;
    private Integer edad;
    
    // Constructor vacío (necesario para Jackson)
    public EstudianteDTO() {
    }
    
    // Constructor con todos los parámetros
    public EstudianteDTO(Long identificador, String nombreCompleto, String email, Integer edad) {
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.edad = edad;
    }
    
    // Getters y Setters
    public Long getIdentificador() {
        return identificador;
    }
    
    public void setIdentificador(Long identificador) {
        this.identificador = identificador;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getEdad() {
        return edad;
    }
    
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}

