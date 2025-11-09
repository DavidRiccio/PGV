package com.procesos.facil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.procesos.facil.controllers.CliController;


@SpringBootApplication
public class DiskMonitorApplication {
    public static void main(String[] args) {
       SpringApplication.run(DiskMonitorApplication.class,args);
    }
    @Bean 
    CommandLineRunner demo(CliController procesos){
        return args -> {
            System.out.println("Iniciando aplicacion");
            procesos.menuConsola();
            System.out.println("Proceso terminado");
        };
    }
}