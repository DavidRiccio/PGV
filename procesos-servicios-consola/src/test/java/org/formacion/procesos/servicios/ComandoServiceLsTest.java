package org.formacion.procesos.servicios;

import org.formacion.procesos.services.ComandoServiceLs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoServiceLsTest {

    ComandoServiceLs comandoServiceLs;

    @BeforeEach
    void before() {
        comandoServiceLs = new ComandoServiceLs();
        comandoServiceLs.setComando("ls");

    }

    @Test
    void validarLaTest() {
        String[] arrayComando = { "ls", "-la" };
        boolean valida = comandoServiceLs.validar(arrayComando);
        Assertions.assertTrue(valida, "funciona");
    }

        @Test
    void validarFalseLaTest() {
        String[] arrayComando = { "ls", "la" };
        boolean valida = comandoServiceLs.validar(arrayComando);
        Assertions.assertFalse(valida, "funciona");
    }

    @Test
    void validarVacioTest() {
        String[] arrayComando = { "ls", " " };
        boolean valida = comandoServiceLs.validar(arrayComando);
        Assertions.assertTrue(valida, "No funciona");
    }

    @Test
    void validarSinVacio(){
        String[] arrayComando = { "ls", "" };
        boolean valida = comandoServiceLs.validar(arrayComando);
        Assertions.assertTrue(valida, "No funciona");

    }
}
