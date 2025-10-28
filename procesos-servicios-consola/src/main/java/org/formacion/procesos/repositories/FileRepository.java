package org.formacion.procesos.repositories;

import org.formacion.procesos.repositories.interfaces.CrudInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Repository
public class FileRepository implements CrudInterface {
    private static Logger logger = LoggerFactory.getLogger(FileRepository.class);
    private String fileName;
    static Path path;

    public void setFileName(String fileName){
        this.fileName = fileName;

    }


    public FileRepository() {
        if (fileName == null){
            fileName = "mis_procesos.txt";

        }
        URL resource = getClass().getClassLoader().getResource(fileName);
        path = Paths.get(resource.getPath());

    }

    @Override
    public boolean add(String text) {
        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            logger.error("Se ha producido un error almacenado en el fichero ", e);
        }
        return false;
    }

}
