package servicios;


import org.junit.jupiter.api.Test;

import com.servicios.MilleniumFalcon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MilleniumFalconTest {
    
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
    public void testMilleniumFalcon_finalizaConEscapeODestruccion() throws InterruptedException {
        MilleniumFalcon m = new MilleniumFalcon();
        
        m.iniciar();
        
        String salida = outputStream.toString();
        
        boolean contieneDestruye = salida.contains("se destruye");
        boolean contieneEscapan = salida.contains("escapan");
        
        assertTrue(contieneDestruye ^ contieneEscapan, 
                   "Debe haber exactamente un resultado: escape o destrucción");
    }
}
