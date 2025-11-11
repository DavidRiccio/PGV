package com.comandos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.comandos.controllers.CliControllers;

@SpringBootApplication
public class ProcesosServiciosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcesosServiciosApplication.class, args);
    }   
}