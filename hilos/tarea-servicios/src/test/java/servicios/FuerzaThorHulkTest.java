package servicios;


import org.junit.jupiter.api.Test;

import com.servicios.FuerzaThorHulk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class FuerzaThorHulkTest {
    
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }
    
    @Test
    public void testFuerzaThorHulk_terminaPorTiempo_yDeclaraResultado() throws InterruptedException {
        FuerzaThorHulk f = new FuerzaThorHulk();
        
        f.iniciar();
        
        String salida = outputStream.toString();
        
        assertTrue(salida.contains("¡Tiempo!"));
        assertTrue(salida.contains("gana") || salida.contains("Empate"));
    }
}
