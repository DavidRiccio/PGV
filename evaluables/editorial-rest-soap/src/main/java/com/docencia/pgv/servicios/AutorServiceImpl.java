package com.docencia.pgv.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.docencia.pgv.interfaces.AutorService;
import com.docencia.pgv.modelo.Autor;
import com.docencia.pgv.repositorios.InMemoryAutorRepository;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    InMemoryAutorRepository inMemoryAutorRepository;

    @Override
    public List<Autor> findAll() {
        return inMemoryAutorRepository.findAll();
    }

    @Override
    public Autor findByIdOrThrow(Long id) {
       return inMemoryAutorRepository.findById(id).orElse(null);
    }

    @Override
    public Autor create(Autor autor) {
        return inMemoryAutorRepository.save(autor);
    }

    @Override
    public void delete(Long id) {
       inMemoryAutorRepository.deleteById(id);
    }

}
