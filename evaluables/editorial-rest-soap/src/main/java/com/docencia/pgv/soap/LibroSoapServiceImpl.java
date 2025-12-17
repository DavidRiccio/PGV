package com.docencia.pgv.soap;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.pgv.interfaces.LibroService;
import com.docencia.pgv.modelo.Libro;

import jakarta.jws.WebService;

@WebService(serviceName = "LibroService", portName = "LibroPortType", endpointInterface = "com.docencia.pgv.soap.LibroSoapService")
@Service
public class LibroSoapServiceImpl implements LibroSoapService {

    private final LibroService libroService;

    public LibroSoapServiceImpl(LibroService libroService) {
        this.libroService = libroService;
    }

    @Override
    public List<Libro> getAllBooks() {
        return libroService.findAll();
    }

    @Override
    public Libro getBookById(Long id) {
        return libroService.findByIdOrThrow(id);
    }
    
    @Override
    public List<Libro> getBooksByAuthor(Long idAutor) {
        return libroService.findByAutorOrThrow(idAutor);
    }

    @Override
    public Libro createBook(String titulo, Integer anioPublicacion, Long idAutor) {
        Libro libro = new Libro(titulo, anioPublicacion, idAutor);
        return libroService.create(libro);
    }

    @Override
    public void deleteBook(Long id) {
        libroService.delete(id);
    }

}
