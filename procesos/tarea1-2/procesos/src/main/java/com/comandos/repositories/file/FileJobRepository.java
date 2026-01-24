package com.comandos.repositories.file;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.comandos.repositories.interfaces.IJobRepository;


@Repository
public class FileJobRepository implements IJobRepository {
    String fileName;
    static Path path;
    private static Logger logger = LoggerFactory.getLogger(IJobRepository.class);
    
    public FileJobRepository(){
        if (fileName==null){
            fileName = "mis_procesos.txt";
        } 

        URL resource = getClass().getClassLoader().getResource(fileName);
        path = Paths.get(resource.getPath());
    }


    @Override
    public boolean add(String texto) {

        try {
            Files.write(path,texto.getBytes(),StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Ha habido un error al guardar en el fichero", e);
        }
        return false;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public Path getpath() {
        throw new UnsupportedOperationException("Unimplemented method 'getpath'");
    }
}