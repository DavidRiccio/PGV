package com.procesos.facil.repositories.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.procesos.facil.repositories.interfaces.IJobRepository;

@Repository
public class FileJobRepository implements IJobRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileJobRepository.class);
    private static final String DEFAULT_FILE = "src/main/resources/stdout.txt";
    private Path path;

    public FileJobRepository() {
        this(DEFAULT_FILE);
    }

    public FileJobRepository(String fileName) {
        try {
            this.path = Paths.get(fileName);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            if (!Files.exists(path)) {
                Files.createFile(path);
                logger.info("Archivo creado: {}", fileName);
            }

        } catch (IOException e) {
            logger.error("Error inicializando archivo: {}", fileName, e);
            throw new RuntimeException("No se pudo inicializar el repositorio", e);
        }
    }

    @Override
    public boolean add(String texto) {
        try {
            Files.writeString(
                    path,
                    texto,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            logger.error("Error al guardar en el fichero", e);
            return false;
        }
    }

    public void setFileName(String fileName) {
        this.path = Paths.get(fileName);
    }
}
