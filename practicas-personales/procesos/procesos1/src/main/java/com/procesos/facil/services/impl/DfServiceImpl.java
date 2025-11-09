package com.procesos.facil.services.impl;

import org.springframework.stereotype.Component;

import com.procesos.facil.domain.Job;
import com.procesos.facil.services.abstracts.CommandoServiceAbstract;

@Component
public class DfServiceImpl extends CommandoServiceAbstract {

    public DfServiceImpl(){
        this.setTipo(Job.DF);
        this.setValidation("^(-h)$");
    }
}
