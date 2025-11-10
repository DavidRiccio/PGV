package com.comandos.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comandos.services.impl.LsofServiceImpl;
import com.comandos.services.impl.PsHeadServiceImpl;
import com.comandos.services.impl.TopServiceImpl;

@Service
public class CliControllers {
    @Autowired
    LsofServiceImpl lsofServiceImpl;
    @Autowired
    PsHeadServiceImpl psHeadServiceImpl;
    @Autowired
    TopServiceImpl topServiceImpl;

    public void menuConsola(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("===Lanzador de Procesos (CLI)===\n" +
                "comandos disponibles:\n" +
                " top -bn1 \n" +
                " lsof -i \n" +
                " ps head (alias de ps aux | head) \n" 
                );
        String comando = scanner.nextLine();
        if (comando.toUpperCase().startsWith("LSOF")){
            lsofServiceImpl.procesarLinea(comando);
        }
        if (comando.toUpperCase().startsWith("TOP")){
            topServiceImpl.procesarLinea(comando);
        }
        if (comando.toUpperCase().startsWith("PS")){
            psHeadServiceImpl.procesarLinea(comando);
        }
    }
}
