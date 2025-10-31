package com.comandos.services.impl;

import org.springframework.stereotype.Component;

import com.comandos.domain.Job;
import com.comandos.services.abstracts.ComandoServiceAbstract;

@Component
public class PsHeadServiceImpl extends ComandoServiceAbstract {

    public PsHeadServiceImpl(){
        this.setTipo(Job.PSHEAD);
        this.setValidation("^(aux)?$");
    }
    
    @Override
    public void procesarLinea(String linea) {
        String[] arrayComando = linea.split("\\s+");
        this.setComando(arrayComando[0]);
        System.out.println("[INF] Comando: " + this.getComando());
        
        if (!validar(arrayComando)) {
            System.out.println("[ERR] Comando invalido: " + linea);
            return;
        }
        
        // Construir el comando real: ps aux | head
        String comandoReal = "ps " + (arrayComando.length > 1 ? arrayComando[1] : "aux") + " | head";
        
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", comandoReal + " > mis_procesos.txt").start();
            ejecutarProceso(proceso);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}