package servicios;

import org.junit.jupiter.api.Test;

import com.servicios.CiudadEnPeligro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CiudadEnPeligroTest {
    
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
    public void testCiudadEnPeligro_soloNeutralizaElOtroSeDetiene() throws InterruptedException {
        // Arrange
        CiudadEnPeligro c = new CiudadEnPeligro();
        
        // Act
        c.iniciar();
        
        // Assert
        String salida = outputStream.toString();
        
        assertTrue(c.amenazaNeutralizada.get());
        
        String ganadorActual = c.ganador.get();
        assertTrue(ganadorActual.equals("Superman") || ganadorActual.equals("Batman"));
        
        // Contar ocurrencias de "Amenaza neutralizada"
        int count = contarOcurrencias(salida, "Amenaza neutralizada");
        assertEquals(1, count, "Solo debe haber una neutralización");
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

