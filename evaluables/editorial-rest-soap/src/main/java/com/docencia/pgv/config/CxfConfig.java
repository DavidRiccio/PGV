package com.docencia.pgv.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.docencia.pgv.soap.AutorSoapServiceImpl;
import com.docencia.pgv.soap.LibroSoapServiceImpl;

import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {
    private final Bus bus;
    private final AutorSoapServiceImpl autorSoapServiceImpl;
    private final LibroSoapServiceImpl libroSoapServiceImpl;

    public CxfConfig(Bus bus, AutorSoapServiceImpl autorSoapServiceImpl,LibroSoapServiceImpl libroSoapServiceImpl){
        this.bus = bus;
        this.autorSoapServiceImpl = autorSoapServiceImpl;
        this.libroSoapServiceImpl = libroSoapServiceImpl;
    }

     @Bean
    public Endpoint autorEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, autorSoapServiceImpl);
        endpoint.publish("/autor");
        return endpoint;
    }

      @Bean
    public Endpoint libroEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, libroSoapServiceImpl);
        endpoint.publish("/libro");
        return endpoint;
    }

    
}
