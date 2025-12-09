package com.docencia.tareas.model;

import java.util.Objects;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "tarea")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tarea {
    private Long id;
    private String titulo;
    private String descripcion;
    private boolean completada;

    /**
     * Constructor vacio
     */
    public Tarea() {
    }

    /**
     * Constructor con el id
     * 
     * @param id id de la tarea
     */
    public Tarea(Long id) {
        this.id = id;
    }

    /**
     * Constructor con todos los parametros
     * 
     * @param id          ide de la tarea
     * @param titulo      titulo de la tarea
     * @param descripcion descripcion de la tarea
     * @param completada  estado de la tarea
     */
    public Tarea(Long id, String titulo, String descripcion, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        return Objects.equals(id, other.id);
    }

}
