package com.docencia.rest.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    private int id;
    private String name;

    /**
     * Constuctor vacio
     */
    public User() {

    }

    /**
     * Constructor de User con parametro nombre
     * 
     * @param name Nombre del usuario
     */
    public User(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        User other = (User) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
