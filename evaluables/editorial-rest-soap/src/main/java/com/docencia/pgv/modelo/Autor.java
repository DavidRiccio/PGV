package com.docencia.pgv.modelo;

public class Autor {

    private Long id;
    private String nombre;
    private String pais;

    /**
     * Constructor vacio
     */
    public Autor() {
    }

    /***
     * Constructor con todos los parametros
     * 
     * @param id     id del autor
     * @param nombre nobre del autor
     * @param pais   pais del autor
     */
    public Autor(Long id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    /***
     * Constructor con nombre y pais
     * 
     * @param nombre nombre del autor
     * @param pais   pais del autor
     */
    public Autor(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
