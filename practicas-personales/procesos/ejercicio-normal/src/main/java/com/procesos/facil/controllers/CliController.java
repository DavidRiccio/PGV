package com.procesos.facil.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.procesos.facil.services.impl.DfServiceImpl;

@Component  // ✅ Cambio: @Component en lugar de @Service
public class CliController implements CommandLineRunner {  // ✅ Implementa CommandLineRunner
    
    @Autowired
    private DfServiceImpl dfService;  // ✅ Cambio: nombre más claro

    @Override  // ✅ Método que se ejecuta automáticamente al iniciar Spring Boot
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        
        // ✅ Loop para repetir el menú
        do {
            mostrarMenu();
            opcion = scanner.nextLine().trim();
            procesarOpcion(opcion);
            
        } while (!opcion.equals("0"));  // ✅ Repite hasta que escriban "0"
        
        System.out.println("\n¡Hasta pronto!");
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n============================================");
        System.out.println("=== Disk Monitor Pro (CLI) Linux ===");
        System.out.println("============================================");
        System.out.println("Comandos disponibles:");
        System.out.println("  1. df -h");
        System.out.println("  2. df -i");
        System.out.println("  3. du -sh /home");
        System.out.println("  c. Limpiar logs");
        System.out.println("  0. Salir");
        System.out.println("============================================");
        System.out.print("Selecciona opción: ");
    }
    
    private void procesarOpcion(String opcion) {
        System.out.println();  // Línea en blanco
        
        switch (opcion.toLowerCase()) {  // ✅ Manejo de todas las opciones
            case "1":
                dfService.procesarLinea("df -h");
                break;
            case "2":
                dfService.procesarLinea("df -i");
                break;
            case "3":
                dfService.procesarLinea("du -sh /home");
                break;
            case "c":
                limpiarLogs();
                break;
            case "0":
                // Salir - el loop terminará
                break;
            default:
                System.out.println("[ERR] Opción inválida: " + opcion);
                break;
        }
    }
    
    private void limpiarLogs() {
        try {
            Files.write(Paths.get("src/main/resources/stdout.txt"), "".getBytes());
            Files.write(Paths.get("src/main/resources/stderr.txt"), "".getBytes());
            System.out.println("✓ Logs limpiados correctamente");
        } catch (Exception e) {
            System.err.println("✗ Error limpiando logs: " + e.getMessage());
        }
    }
}
