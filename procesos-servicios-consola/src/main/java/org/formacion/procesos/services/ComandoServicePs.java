package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServiceAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoServicePs extends ComandoServiceAbstract {
    public ComandoServicePs(){
        this.setTipo(ProcessType.PS);
        this.setValidation("^((-?(xa|x|aux|a))|\s*)$");
    }
}

