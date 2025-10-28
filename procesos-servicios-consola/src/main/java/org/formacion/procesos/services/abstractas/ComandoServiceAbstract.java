package org.formacion.procesos.services.abstractas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.*;
import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.repositories.interfaces.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComandoServiceAbstract {
    private String comando;
    private ProcessType tipo;
    private String validation;

    CrudInterface fileRepository;

    public CrudInterface getFileRepository(){
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(CrudInterface fileRepository){
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

    public ProcessType getTipo() {
        return tipo;
    }

    public void setTipo(ProcessType tipo) {
        this.tipo = tipo;
    }

    public void procesarLinea(String linea) {
        String[] arrayComando = linea.split("\s+");
        this.setComando(arrayComando[0]);
        System.out.println("Comando: " + this.getComando());
        if (!validar(arrayComando)) {
            System.out.println("Comando invalido");
            return;
        }

        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea + " > mis_procesos.txt").start();
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
            return false;
        }
        String parametro = arrayComando[1];
        Pattern pattern = Pattern.compile(validation);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public boolean validarComando() {
        if (!this.getComando().toUpperCase().equals(getTipoToString())) {
            System.out.println("Comando invalido");
            return false;
        }
        return true;
    }
}
