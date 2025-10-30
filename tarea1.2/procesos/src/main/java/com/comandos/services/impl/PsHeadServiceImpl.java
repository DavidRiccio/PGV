package com.comandos.services.impl;

import org.springframework.stereotype.Component;

import com.comandos.domain.Job;
import com.comandos.services.abstracts.ComandoServiceAbstract;

@Component
public class PsHeadServiceImpl extends ComandoServiceAbstract {
    public PsHeadServiceImpl(){
        this.setTipo(Job.PSHEAD);
        
    }
    
}
