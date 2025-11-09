package servicios;  // Usa el mismo paquete que Tardis

import org.junit.jupiter.api.Test;

import com.servicios.Tardis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TardisTest {
    
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
    public void testTardis_existeUnaEraGanadora() throws InterruptedException {
        // Arrange
        Tardis t = new Tardis();
        
        // Act
        t.iniciar();
        
        // Assert
        String salida = outputStream.toString();
        
        assertTrue(t.destinoAlcanzado.get());
        assertNotNull(t.eraGanadora.get());
        
        // Contar ocurrencias de "llegó primero"
        int count = contarOcurrencias(salida, "llegó primero");
        assertEquals(1, count, "Solo una era debe ganar");
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
