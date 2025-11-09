package com.procesos.facil.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.procesos.facil.services.impl.DfServiceImpl;

@Service
public class CliController {
    @Autowired
    DfServiceImpl dhServiceImpl;


    public void menuConsola(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(("===Lanzador de Procesos (CLI)===\n" +
                "comandos disponibles:\n" +
                " dh -f \n" 
        ));

        String comando = scanner.nextLine();
        if (comando.toUpperCase().startsWith("DF"));{
            dhServiceImpl.procesarLinea(comando);
        }
    }
}
