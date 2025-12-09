package com.docencia.tareas.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.docencia.tareas.soap.TareaSoapService;

import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final TareaSoapService tareaSoapEndpoint;

    public CxfConfig(Bus bus, TareaSoapService tareaSoapEndpoint) {
        this.bus = bus;
        this.tareaSoapEndpoint = tareaSoapEndpoint;
    }

    @Bean
    public Endpoint tareaEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, tareaSoapEndpoint);
        endpoint.publish("/tareas");
        return endpoint;
    }
}
