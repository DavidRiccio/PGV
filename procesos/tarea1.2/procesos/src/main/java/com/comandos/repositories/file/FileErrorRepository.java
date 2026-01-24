package com.comandos.repositories.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FileErrorRepository {

    public static Path path;
    public static Logger logger = LoggerFactory.getLogger(FileErrorRepository.class);

    public FileErrorRepository() {
        path = Paths.get("stderr.txt");
    }

    public boolean addError(String mensaje) {
        try {
            String error = "[ERR] " + mensaje + "\n";
            Files.write(path, error.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            logger.error("Error al escribir en stderr", e);
            return false;
        }
    }
}
