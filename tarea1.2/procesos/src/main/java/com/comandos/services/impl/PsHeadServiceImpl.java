package com.comandos.services.impl;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.comandos.domain.Job;
import com.comandos.services.abstracts.ComandoServiceAbstract;

@Component
public class PsHeadServiceImpl extends ComandoServiceAbstract {

    public PsHeadServiceImpl() {

        this.setTipo(Job.PS);

        this.setValidation("^head$");
    }

    @Override
    public void procesarLinea(String linea) {
        String[] arrayComando = linea.trim().split("\\s+");
        this.setComando(arrayComando[0]);

        if (!validar(arrayComando)) {
            System.out.println("[ERR] Comando invalido: " + linea);
            errorRepository.addError("Comando invalido: " + Arrays.toString(arrayComando));

            
            return;
        }

        String comandoReal = "ps aux | head";

        System.out.println("[INF] Ejecutando: " + comandoReal);

        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", comandoReal + " > mis_procesos.txt").start();
            ejecutarProceso(proceso);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}