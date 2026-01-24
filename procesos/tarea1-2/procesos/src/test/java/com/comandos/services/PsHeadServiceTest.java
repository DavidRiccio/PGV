package com.comandos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comandos.repositories.file.FileErrorRepository;
import com.comandos.services.impl.PsHeadServiceImpl;

class PsHeadServiceTest {
    
    PsHeadServiceImpl comandoHeadServiceTest;

    @BeforeEach
    void beforeEach(){
        comandoHeadServiceTest = new PsHeadServiceImpl();
        comandoHeadServiceTest.setComando("ps"); 

        FileErrorRepository errorRepository = new FileErrorRepository();
        comandoHeadServiceTest.setErrorRepository(errorRepository);
    }

    @Test
    void validarComandoCorrectoTest(){
        String [] arrayComando = {"ps","head"};
        boolean valida = comandoHeadServiceTest.validar(arrayComando);
        Assertions.assertTrue(valida,"La validación de 'ps head' debería ser correcta");
    }
    
    @Test
    void validarParametroIncorrectoTest(){
        String [] arrayComando = {"ps","aux"};
        boolean valida = comandoHeadServiceTest.validar(arrayComando);
        Assertions.assertFalse(valida,"'aux' no es un parámetro válido, debería dar false");
    }

    @Test
    void validarParametroVacioTest(){
        String [] arrayComando = {"ps"," "};
        boolean valida = comandoHeadServiceTest.validar(arrayComando);
        Assertions.assertFalse(valida,"Un espacio no es 'head', debería dar false");
    }

    @Test
    void validarParametroSinVacioTest(){
        String [] arrayComando = {"ps",""};
        boolean valida = comandoHeadServiceTest.validar(arrayComando);
        Assertions.assertFalse(valida,"Una cadena vacía no es 'head', debería dar false");
    }

    @Test
    void validarParametroFalsoTest(){
        String [] arrayComando = {"ps","lalalala"};
        boolean valida = comandoHeadServiceTest.validar(arrayComando);
        Assertions.assertFalse(valida,"'lalalala' no es 'head', debería dar false");
    }
}