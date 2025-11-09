package servicios;

import org.junit.jupiter.api.Test;

import com.servicios.BatallaPokemon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BatallaPokemonTest {
    
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
    public void testBatallaPokemon_debeHaberGanador() throws InterruptedException {
        // Arrange
        BatallaPokemon juego = new BatallaPokemon();
        
        // Act
        juego.iniciar();
        
        // Assert
        String salida = outputStream.toString();
        assertTrue(salida.contains("ha ganado la batalla!"));
    }
}

