package org.formacion.procesos.services.abstractas;

import java.util.List;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComandoServiceAbstract {
    String comando;
    List<String> parametros;
    ProcessType tipo;


    @Autowired
    FileRepository fileRepository;

    public String getComando() {
        return comando;
    }
    public void setComando(String comando) {
        this.comando = comando;
    }
    public List<String> getParametros() {
        return parametros;
    }
    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }
    public ProcessType getTipo() {
        return tipo;
    }
    public void setTipo(ProcessType tipo) {
        this.tipo = tipo;
    }

    public void procesarLinea(String linea){
        String[] arrayComando = linea.split(" ");
        this.setComando(arrayComando[0]);
        System.out.println("Comando: "+ this.getComando());
        if(!validar(arrayComando)){
            System.out.println("Comando invalido");
            return ;
        }
        
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh","-c",linea + "> mis procesos").start();
            ejecutarProceso(proceso);

        }catch (Exception e){
            e.printStackTrace();
        }
        
        imprimeMensaje();

    }

    public boolean ejecutarProceso(Process proceso){
        try{
            proceso.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public String getTipoToString(){
        if (tipo==null){
            return null;
        }
        return tipo.toString();
    }

    public abstract void imprimeMensaje();
    public abstract boolean validar(String[] arrayComando);
    public boolean validarComando() {
        if(!this.getComando().toUpperCase().equals(getTipoToString())){
            System.out.println("Comando invalido");
            return false ;
        }
        return true;
    }
}
