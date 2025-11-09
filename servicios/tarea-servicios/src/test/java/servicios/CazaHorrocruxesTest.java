package servicios;

import org.junit.jupiter.api.Test;

import com.servicios.CazaHorrocruxes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CazaHorrocruxesTest {

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
    public void testCazaHorrocruxes_unGanadorYUnSoloHallazgo() throws InterruptedException {
        // Arrange
        CazaHorrocruxes sim = new CazaHorrocruxes();

        // Act
        sim.iniciar();

        // Assert
        String salida = outputStream.toString();

        assertTrue(sim.encontrado.get());

        String ganadorActual = sim.ganador.get();
        assertTrue(ganadorActual.equals("Harry") ||
                ganadorActual.equals("Hermione") ||
                ganadorActual.equals("Ron"));

        // Contar ocurrencias de "encontró un Horrocrux"
        int count = contarOcurrencias(salida, "encontró un Horrocrux");
        assertEquals(1, count, "Solo debe haber un hallazgo de Horrocrux");
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
