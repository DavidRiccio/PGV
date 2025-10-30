package com.comandos.repositories.interfaces;

import java.nio.file.Path;

public interface IJobRepository {
    public boolean add(String texto);
    public Path getpath();
    
    
}
