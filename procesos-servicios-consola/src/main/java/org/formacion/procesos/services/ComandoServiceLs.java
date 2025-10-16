package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServiceAbstract;

public class ComandoServiceLs extends ComandoServiceAbstract {
    public ComandoServiceLs(){
        this.setTipo(ProcessType.LS);
    }

    @Override
    public void imprimeMensaje(){
        System.out.println("Estoy llamando a comandoControllerLS");
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

