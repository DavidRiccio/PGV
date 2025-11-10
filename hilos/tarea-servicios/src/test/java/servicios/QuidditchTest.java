package servicios;


import org.junit.jupiter.api.Test;

import com.servicios.Quidditch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class QuidditchTest {
    
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
    public void testQuidditch_terminaCuandoSnitchAtrapada() throws InterruptedException {
        Quidditch q = new Quidditch();
        
        q.iniciar();
        
        String salida = outputStream.toString();
        
        assertTrue(salida.contains("¡Snitch dorada atrapada!"));
        assertTrue(salida.contains("Marcador final:"));
    }
}
