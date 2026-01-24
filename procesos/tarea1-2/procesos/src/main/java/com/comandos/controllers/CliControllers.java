package com.comandos.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.comandos.services.impl.LsofServiceImpl;
import com.comandos.services.impl.PsHeadServiceImpl;
import com.comandos.services.impl.TopServiceImpl;

@Component
public class CliControllers implements CommandLineRunner {
    @Autowired
    LsofServiceImpl lsofServiceImpl;
    @Autowired
    PsHeadServiceImpl psHeadServiceImpl;
    @Autowired
    TopServiceImpl topServiceImpl;

   @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner (System.in);
        String opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextLine().trim();
            procesarOpcion(opcion);
        } while (!opcion.equals("0"));
    }


    private void mostrarMenu() {
        System.out.println("\n============================================");
        System.out.println("=== Disk Monitor Pro (CLI) Linux ===");
        System.out.println("============================================");
        System.out.println("Comandos disponibles:");
        System.out.println("  1. top -bn1");
        System.out.println("  2. lsof -i");
        System.out.println("  3. ps head");
        System.out.println("  c. Limpiar logs (Pulsar c)");
        System.out.println("  0. Salir (Pulsar 0)");
        System.out.println("============================================");
        System.out.print("Selecciona opción: ");
    }

 private void procesarOpcion(String opcion) {
        System.out.println();  
        
        switch (opcion.toLowerCase()) {  
            case "top -bn1":
                topServiceImpl.procesarLinea("top -bn1");
                break;
            case "lsof -i":
                lsofServiceImpl.procesarLinea("lsof -i");
                break;
            case "ps head":
                psHeadServiceImpl.procesarLinea("ps aux | head");
                break;
            case "c":
                limpiarLogs();
                break;
            case "0":
                break;
            default:
                System.out.println("[ERR] Opción inválida: " + opcion);
                break;
        }
    }

    private void limpiarLogs() {
        try {
            Files.write(Paths.get("stdout.txt"), "".getBytes());
            Files.write(Paths.get("stderr.txt"), "".getBytes());
            System.out.println("✓ Logs limpiados correctamente");
        } catch (Exception e) {
            System.err.println("✗ Error limpiando logs: " + e.getMessage());
        }
    }
    
}
