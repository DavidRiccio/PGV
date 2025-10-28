package org.formacion.procesos.servicios;

import org.formacion.procesos.services.ComandoServicePs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComandoServicePsTest {

    ComandoServicePs comandoServicePs;

    @BeforeEach
    void before() {
        comandoServicePs = new ComandoServicePs();
        comandoServicePs.setComando("ps");

    }

    @Test
    void validarXaTest() {
        String[] arrayComando = { "ps", "-xa" };
        boolean valida = comandoServicePs.validar(arrayComando);
        Assertions.assertTrue(valida, "funciona");
    }

        @Test
    void validarXaTest2() {
        String[] arrayComando = { "ps", "xa" };
        boolean valida = comandoServicePs.validar(arrayComando);
        Assertions.assertTrue(valida, "funciona");
    }
        @Test
    void validarFalseXaTest() {
        String[] arrayComando = { "ps", "lalalal" };
        boolean valida = comandoServicePs.validar(arrayComando);
        Assertions.assertFalse(valida, "funciona");
    }

    @Test
    void validarVacioTest() {
        String[] arrayComando = { "ps", "" };
        boolean valida = comandoServicePs.validar(arrayComando);
        Assertions.assertTrue(valida, "No funciona");
    }

    @Test
    void validarSinVacio(){
        String[] arrayComando = { "ps", "" };
        boolean valida = comandoServicePs.validar(arrayComando);
        Assertions.assertTrue(valida, "No funciona");

    }
}
