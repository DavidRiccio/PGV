package com.docencia.semaforo;


public enum Colors {
    RED("Rojo"), GREEN("Verde"), AMBER("Ambar");

    private final String name;

    Colors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String nameToUpper() {
        return name.toUpperCase();
    }

    public String nameToLower() {
        return name.toLowerCase();
    }
}
