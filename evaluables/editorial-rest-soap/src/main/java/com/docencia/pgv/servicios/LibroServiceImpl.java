package com.docencia.pgv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.docencia.pgv.interfaces.LibroService;
import com.docencia.pgv.modelo.Libro;
import com.docencia.pgv.repositorios.InMemoryLibroRepository;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    InMemoryLibroRepository inMemoryLibroRepository;

    @Override
    public List<Libro> findAll() {
       return inMemoryLibroRepository.findAll();
    }

    @Override
    public Libro findByIdOrThrow(Long id) {
        return inMemoryLibroRepository.findById(id).orElse(null);
    }

    @Override
    public List<Libro> findByAutorOrThrow(Long idAutor) {
        return inMemoryLibroRepository.findByIdAutor(idAutor);
    }

    @Override
    public Libro create(Libro libro) {
        return inMemoryLibroRepository.save(libro);
    }

    @Override
    public void delete(Long id) {
       inMemoryLibroRepository.deleteById(id);
    }
}
