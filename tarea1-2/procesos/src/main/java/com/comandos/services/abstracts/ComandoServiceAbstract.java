package com.comandos.services.abstracts;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

import com.comandos.domain.Job;
import com.comandos.repositories.file.FileErrorRepository;
import com.comandos.repositories.file.FileJobRepository;

public abstract class ComandoServiceAbstract {
    private String comando;
    private Job tipo;
    private String validation;
    protected List<String> parametrosValidos;
    @Autowired
    public FileErrorRepository fileErrorRepository;
    private static final List<String> COMANDOS_PERMITIDOS = Arrays.asList(
        "top -bn1",
        "lsof -i",
        "ps head"
    );

    public ComandoServiceAbstract(String... parametrosValidos) {
        this.parametrosValidos = Arrays.asList(parametrosValidos);
    }

    public FileErrorRepository getErrorRepository() {
        return fileErrorRepository;
    }

    @Autowired
    public void setErrorRepository(FileErrorRepository errorRepository) {
        this.fileErrorRepository = errorRepository;
    }

    FileJobRepository fileRepository;

    public FileJobRepository getFileRepository() {
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(FileJobRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Job getTipo() {
        return tipo;
    }

    public void setTipo(Job tipo) {
        this.tipo = tipo;
    }

    public void procesarLinea(String linea) {
     System.out.println("[INF] Procesando comando: " + linea);
        
        if (!esComandoValido(linea)) {
            System.out.println("[ERR] Comando no permitido: " + linea);
            fileErrorRepository.addError("Comando no permitido: " + linea);
            return;
        }
        
        // Ejecutar el proceso
        ejecutarComando(linea,"stdout.txt");
    }
    private boolean esComandoValido(String comando) {
        String comandoLimpio = comando.trim();
        
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
private void ejecutarComando(String comando, String archivoDestino) {
    try {
        System.out.println(">>> Ejecutando: " + comando + " > " + archivoDestino + "\n");

        // El comando con pipe se pasa completo al shell
        String comandoConRedireccion = comando + " > " + archivoDestino;

        // IMPORTANTE: sh -c interpreta el pipe correctamente
        ProcessBuilder pb = new ProcessBuilder("sh", "-c", comandoConRedireccion);
        Process proceso = pb.start();

        // Leer stderr
        byte[] stderrBytes = proceso.getErrorStream().readAllBytes();
        String stderr = new String(stderrBytes);

        if (!stderr.isEmpty()) {
            System.out.println("[ERR] " + stderr);
            fileErrorRepository.addError("[" + comandoConRedireccion + "]\n" + stderr + "\n");
        }

        int exitCode = proceso.waitFor();

        if (exitCode != 0) {
            String mensajeError = "El comando falló con exit code: " + exitCode;
            System.err.println(mensajeError);
            fileErrorRepository.addError("[" + comandoConRedireccion + "]\n" + mensajeError + "\n");
        }

        System.out.println("\n========================================");
        System.out.println("Resultado:");
        System.out.println("  - Exit Code: " + exitCode);
        System.out.println("  - Guardado en: " + archivoDestino);
        System.out.println("========================================");

    } catch (Exception e) {
        System.err.println("Error ejecutando comando: " + e.getMessage());
        fileErrorRepository.addError("Error de ejecución: " + e.getMessage() + "\n");
        e.printStackTrace();
    }
}




    public boolean ejecutarProceso(Process proceso) {
        try {
            proceso.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getTipoToString() {
        if (tipo == null) {
            return null;
        }
        return tipo.toString();
    }

    public boolean validar(String[] arrayComando) {
        if (!validarComando()) {
            fileErrorRepository.addError("Comando invalido: " + this.getComando() + Arrays.toString(arrayComando));
            return false;
        }
        String parametro = arrayComando[1];
        Pattern pattern = Pattern.compile(validation);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            fileErrorRepository.addError("Comando invalido: " + this.getComando() + Arrays.toString(arrayComando));
            return false;
        }
        return true;
    }

    public boolean validarComando() {
        if (!this.getComando().toUpperCase().equals(getTipoToString())) {
            return false;
        }
        return true;
    }
    public static List<String> getComandosPermitidos() {
        return COMANDOS_PERMITIDOS;
    }
}