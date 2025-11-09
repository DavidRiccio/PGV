package servicios;


import org.junit.jupiter.api.Test;

import com.servicios.ExploradoresJedi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ExploradoresJediTest {
    
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
    public void testExploradoresJedi_unSoloGanador() throws InterruptedException {
        ExploradoresJedi e = new ExploradoresJedi();
        
        e.iniciar();
        
        String salida = outputStream.toString();
        
        assertTrue(e.pistaEncontrada.get());
        
        int count = contarOcurrencias(salida, "halló una pista");
        assertEquals(1, count, "Solo un Jedi debe encontrar la pista");
    }
    
    private int contarOcurrencias(String texto, String subcadena) {
        int count = 0;
        int index = 0;
        while ((index = texto.indexOf(subcadena, index)) != -1) {
            count++;
            index += subcadena.length();
        }
        return count;
    }
}
