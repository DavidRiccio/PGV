package com.comandos.services.impl;

import org.springframework.stereotype.Component;

import com.comandos.domain.Job;
import com.comandos.services.abstracts.ComandoServiceAbstract;

@Component
public class LsofServiceImpl extends ComandoServiceAbstract{
    
    public LsofServiceImpl(){
        this.setTipo(Job.LSOF);
        this.setValidation("^(-i|\s*)$");
    }
}
