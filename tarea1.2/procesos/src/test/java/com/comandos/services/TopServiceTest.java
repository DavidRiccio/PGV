package com.comandos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comandos.repositories.file.FileErrorRepository;
import com.comandos.services.impl.TopServiceImpl;

class TopServiceTest {
    
    TopServiceImpl comandoTopService;

    @BeforeEach
    void beforeEach(){
        comandoTopService = new TopServiceImpl();
        comandoTopService.setComando("top"); 

        FileErrorRepository errorRepository = new FileErrorRepository();
        comandoTopService.setErrorRepository(errorRepository);
    }


    @Test
    void validarParametroCorrectoTest(){
        String [] arrayComando = {"top","-bn1"};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertTrue(valida,"La validación de '-bn1' debería ser correcta");
    }

    @Test
    void validarParametroCorrectoOtroDigitoTest(){
        String [] arrayComando = {"top","-bn5"};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertTrue(valida,"La validación de '-bn5' debería ser correcta");
    }


    @Test
    void validarParametroIncompletoTest(){
        String [] arrayComando = {"top","-bn"};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertFalse(valida,"'-bn' (sin número) debería ser inválido");
    }

    @Test
    void validarParametroDemasiadoLargoTest(){
        String [] arrayComando = {"top","-bn10"};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertFalse(valida,"'-bn10' (dos dígitos) debería ser inválido");
    }

    @Test
    void validarParametroIncorrectoTest(){
        String [] arrayComando = {"top","-i"};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertFalse(valida,"'-i' no coincide con el regex, debería ser inválido");
    }

    @Test
    void validarParametroVacioTest(){
        String [] arrayComando = {"top",""};
        boolean valida = comandoTopService.validar(arrayComando);
        Assertions.assertFalse(valida,"Un parámetro vacío debería ser inválido");
    }
}