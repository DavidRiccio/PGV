package com.procesos.facil.services.abstracts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.procesos.facil.repositories.files.FileErrorRepository;
import com.procesos.facil.repositories.files.FileJobRepository;

public abstract class CommandoServiceAbstract {

    // WHITELIST: Lista de comandos completos permitidos
    private static final List<String> COMANDOS_PERMITIDOS = Arrays.asList(
        "df -h",
        "df -i",
        "du -sh /home"
    );

    @Autowired
    public FileErrorRepository fileErrorRepository;
    
    @Autowired
    FileJobRepository fileRepository;

    /**
     * Procesa y ejecuta una línea de comando
     */
    public void procesarLinea(String linea) {
        System.out.println("[INF] Procesando comando: " + linea);
        
        // Validar con whitelist
        if (!esComandoValido(linea)) {
            System.out.println("[ERR] Comando no permitido: " + linea);
            fileErrorRepository.addError("Comando no permitido: " + linea);
            return;
        }
        
        // Ejecutar el proceso
        ejecutarComando(linea);
    }
    
    /**
     * Validación con WHITELIST (más seguro que regex)
     */
    private boolean esComandoValido(String comando) {
        String comandoLimpio = comando.trim();
        
        // Verificar si está en la whitelist
        if (!COMANDOS_PERMITIDOS.contains(comandoLimpio)) {
            return false;
        }
        
        // Detectar caracteres peligrosos (defensa adicional)
        String[] caracteresPeligrosos = {"&&", "||", ";", "|", "`", "$", "(", ")"};
        for (String peligroso : caracteresPeligrosos) {
            if (comando.contains(peligroso)) {
                System.err.println("[SECURITY] Carácter peligroso detectado: " + peligroso);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Ejecuta el comando y captura stdout/stderr en tiempo real
     */
    private void ejecutarComando(String comando) {
        try {
            System.out.println(">>> Ejecutando: " + comando + "\n");
            
            // Crear proceso SIN redirección (la capturamos en Java)
            ProcessBuilder pb = new ProcessBuilder("sh", "-c", comando);
            Process proceso = pb.start();
            
            // Capturar stdout
            BufferedReader stdoutReader = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
            );
            
            // Capturar stderr
            BufferedReader stderrReader = new BufferedReader(
                new InputStreamReader(proceso.getErrorStream())
            );
            
            StringBuilder stdout = new StringBuilder();
            StringBuilder stderr = new StringBuilder();
            
            // Leer stdout línea por línea
            String linea;
            while ((linea = stdoutReader.readLine()) != null) {
                System.out.println("[OUT] " + linea);
                stdout.append(linea).append("\n");
            }
            
            // Leer stderr línea por línea
            while ((linea = stderrReader.readLine()) != null) {
                System.out.println("[ERR] " + linea);
                stderr.append(linea).append("\n");
            }
            
            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();
            
            // Guardar en archivos
            if (!stdout.toString().isEmpty()) {
                fileRepository.add("[" + comando + "]\n" + stdout.toString() + "\n");
            }
            
            if (!stderr.toString().isEmpty()) {
                fileErrorRepository.addError("[" + comando + "]\n" + stderr.toString() + "\n");
            }
            
            // Mostrar resumen
            System.out.println("\n========================================");
            System.out.println("Resultado:");
            System.out.println("  - Exit Code: " + exitCode);
            System.out.println("  - Guardado en: stdout.txt");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("Error ejecutando comando: " + e.getMessage());
            fileErrorRepository.addError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Obtener lista de comandos permitidos
     */
    public static List<String> getComandosPermitidos() {
        return COMANDOS_PERMITIDOS;
    }
}
