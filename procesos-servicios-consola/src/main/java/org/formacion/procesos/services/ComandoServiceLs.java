package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServiceAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoServiceLs extends ComandoServiceAbstract {
    public ComandoServiceLs(){
        this.setTipo(ProcessType.LS);
        this.setValidation("^((-(la|l|a))|\s*)$");
    }

}

