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
    private final Path path;

    public FileJobRepository() {
        try {
            this.path = Paths.get(DEFAULT_FILE);
            
            System.out.println("[INFO] Ruta del archivo: " + path.toAbsolutePath());

            // Crear directorio si no existe
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // Crear archivo si no existe
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("[INFO] Archivo creado: " + path.toAbsolutePath());
            } else {
                System.out.println("[INFO] Archivo ya existe: " + path.toAbsolutePath());
            }

        } catch (IOException e) {
            logger.error("Error inicializando archivo: {}", DEFAULT_FILE, e);
            throw new RuntimeException("No se pudo inicializar el repositorio", e);
        }
    }

    @Override
    public boolean add(String texto) {
        try {
            System.out.println("[DEBUG] Guardando " + texto.length() + " caracteres en: " + path.toAbsolutePath());
            
            Files.writeString(
                path,
                texto + "\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            
            System.out.println("[DEBUG] ✓ Guardado exitosamente");
            return true;
            
        } catch (IOException e) {
            logger.error("Error al guardar en el fichero", e);
            System.err.println("[ERROR] No se pudo escribir: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
