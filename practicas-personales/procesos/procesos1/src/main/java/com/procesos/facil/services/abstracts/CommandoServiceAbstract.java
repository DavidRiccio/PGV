package com.procesos.facil.services.abstracts;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.procesos.facil.domain.Job;
import com.procesos.facil.repositories.files.FileErrorRepository;
import com.procesos.facil.repositories.files.FileJobRepository;

public class CommandoServiceAbstract {
    private String comando;
    private Job tipo;
    private String validation;


    @Autowired
    public FileErrorRepository fileErrorRepository;

     FileJobRepository fileRepository;

    public FileJobRepository getFileRepository() {
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(FileJobRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String getComando() {
        return this.comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Job getTipo() {
        return this.tipo;
    }

    public void setTipo(Job tipo) {
        this.tipo = tipo;
    }

    public String getValidation() {
        return this.validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public FileErrorRepository getFileErrorRepository() {
        return this.fileErrorRepository;
    }

    public void setFileErrorRepository(FileErrorRepository fileErrorRepository) {
        this.fileErrorRepository = fileErrorRepository;
    }
        public String getTipoToString() {
        if (tipo == null) {
            return null;
        }
        return tipo.toString();
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
            proceso = new ProcessBuilder("sh", "-c", linea + "> src/main/resources/stdout.txt").start();
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
}