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
    @Bean
    CommandLineRunner demo(CliControllers procesos) {
        return args -> {
            System.out.println("Iniciando proceso al arrancar la aplicación...");

            procesos.menuConsola();

            System.out.println("Proceso finalizado.");
        };
    }
}