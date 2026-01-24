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
    public FileErrorRepository errorRepository;

    public ComandoServiceAbstract(String... parametrosValidos) {
        this.parametrosValidos = Arrays.asList(parametrosValidos);
    }

    public FileErrorRepository getErrorRepository() {
        return errorRepository;
    }

    @Autowired
    public void setErrorRepository(FileErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
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
        String[] arrayComando = linea.split("\s+");
        this.setComando(arrayComando[0]);
        System.out.println("[INF] Comando: " + this.getComando());
        if (!validar(arrayComando)) {
            System.out.println("[ERR] Comando invalido");
            return;
        }

        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea + "> mis_procesos.txt").start();
            ejecutarProceso(proceso);

        } catch (Exception e) {
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
            errorRepository.addError("Comando invalido: " + this.getComando() + Arrays.toString(arrayComando));
            return false;
        }
        String parametro = arrayComando[1];
        Pattern pattern = Pattern.compile(validation);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            errorRepository.addError("Comando invalido: " + this.getComando() + Arrays.toString(arrayComando));
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

}