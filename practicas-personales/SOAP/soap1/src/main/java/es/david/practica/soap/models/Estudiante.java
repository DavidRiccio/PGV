package es.david.practica.soap.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "email")
    String email;
    @ManyToOne
    Curso curso;

    public Estudiante() {
    }

    public Estudiante(Long id) {
        this.id = id;
    }

    public Estudiante(Long id, String nombre, String email, Curso curso) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}
