package com.docencia.pgv.soap;

import java.util.List;
import org.springframework.stereotype.Service;

import com.docencia.pgv.interfaces.AutorService;
import com.docencia.pgv.modelo.Autor;
import jakarta.jws.WebService;

@WebService(serviceName = "AutorService", portName = "AutorPortType", endpointInterface = "com.docencia.pgv.soap.AutorSoapService")
@Service
public class AutorSoapServiceImpl implements AutorSoapService {

    private final AutorService autorService;

    public AutorSoapServiceImpl(AutorService autorService) {
        this.autorService = autorService;
    }

    @Override
    public List<Autor> getAllAuthors() {
        return autorService.findAll();
    }

    @Override
    public Autor getAuthorById(Long id) {
        return autorService.findByIdOrThrow(id);
    }

    @Override
    public Autor createAuthor(String nombre, String pais) {
        Autor autor = new Autor(nombre, pais);
        return autorService.create(autor);
    }

    @Override
    public void deleteAuthor(Long id) {
        autorService.delete(id);
    }
}
