package com.comandos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comandos.repositories.file.FileErrorRepository;
import com.comandos.services.impl.LsofServiceImpl;
import java.lang.Process;

class LsofServiceTest {

    LsofServiceImpl comandoLsofService;
    FileErrorRepository errorRepository;

    boolean ejecutarProcesoFueLlamado;

    @BeforeEach
    void beforeEach() {

        ejecutarProcesoFueLlamado = false;

        comandoLsofService = new LsofServiceImpl() {
            @Override
            public boolean ejecutarProceso(Process proceso) {

                ejecutarProcesoFueLlamado = true;
                return true;
            }
        };

        errorRepository = new FileErrorRepository();
        comandoLsofService.setErrorRepository(errorRepository);
    }

    @Test
    void testValidar_ComandoCorrecto_ParametroCorrecto() {

        String[] arrayComando = { "lsof", "-i" };

        comandoLsofService.setComando(arrayComando[0]);

        boolean valida = comandoLsofService.validar(arrayComando);

        Assertions.assertTrue(valida, "'lsof -i' debería ser una validación exitosa");
    }

    @Test
    void testValidar_ComandoCorrecto_ParametroVacio() {

        String[] arrayComando = { "lsof", "" };
        comandoLsofService.setComando(arrayComando[0]);

        boolean valida = comandoLsofService.validar(arrayComando);
        Assertions.assertTrue(valida, "'lsof' (con parámetro vacío) debería ser válido");
    }

    @Test
    void testValidar_ComandoCorrecto_ParametroIncorrecto() {

        String[] arrayComando = { "lsof", "lalalala" };
        comandoLsofService.setComando(arrayComando[0]);

        boolean valida = comandoLsofService.validar(arrayComando);

        Assertions.assertFalse(valida, "'lalalala' debería fallar el regex");
    }

    @Test
    void testValidar_ComandoIncorrecto() {

        String[] arrayComando = { "top", "-i" };
        comandoLsofService.setComando(arrayComando[0]);

        boolean valida = comandoLsofService.validar(arrayComando);

        Assertions.assertFalse(valida, "El comando 'top' debería fallar en LsofService");
    }

    @Test
    void testValidar_CRASH_SinParametro() {

        String[] arrayComando = { "lsof" };
        comandoLsofService.setComando(arrayComando[0]);

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            comandoLsofService.validar(arrayComando);
        }, "Debería lanzar ArrayIndexOutOfBoundsException al acceder a arrayComando[1]");
    }

    @Test
    void testProcesarLinea_ComandoValido_EjecutaProceso() {

        comandoLsofService.procesarLinea("lsof -i");

        Assertions.assertTrue(ejecutarProcesoFueLlamado,
                "El proceso DEBIÓ ejecutarse para un comando válido");
    }

    @Test
    void testProcesarLinea_ComandoInvalido_NoEjecutaProceso() {

        comandoLsofService.procesarLinea("lsof lalalala");

        Assertions.assertFalse(ejecutarProcesoFueLlamado,
                "El proceso NO DEBIÓ ejecutarse para un parámetro inválido");
    }

}