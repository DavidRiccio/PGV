package servicios;

import org.junit.jupiter.api.Test;

import com.servicios.BatallaMagos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BatallaMagosTest {

    final PrintStream originalOut = System.out;
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    public void testBatallaMagos_debeHaberGanadorYTerminar() throws InterruptedException {
        BatallaMagos b = new BatallaMagos();

        b.iniciar();

        String salida = outputStream.toString();
        assertTrue(salida.contains("gana la batalla mágica."));
    }
}
