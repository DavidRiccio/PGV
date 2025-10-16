package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServiceAbstract;

public class ComandoServicePs extends ComandoServiceAbstract {
    public ComandoServicePs(){
        this.setTipo(ProcessType.PS);
    }

    @Override
    public void imprimeMensaje(){
        System.out.println("Estoy llamando a comandoControllerPS");
    }

    @Override
    public boolean validar(String[] arrayComando) {
            if(!super.validarComando()){
                return false;
        }
        String parametro = arrayComando[1];
        return true;
    }
}

