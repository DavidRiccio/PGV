package com.comandos.services.impl;

import org.springframework.stereotype.Component;

import com.comandos.domain.Job;
import com.comandos.services.abstracts.ComandoServiceAbstract;
@Component
public class TopServiceImpl extends ComandoServiceAbstract{
    
    public TopServiceImpl(){
        this.setTipo(Job.TOP);
        this.setValidation("-b");
    }
}
