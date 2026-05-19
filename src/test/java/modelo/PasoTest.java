/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author DAVID
 */
import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*;

public class PasoTest {
    
     @Test
    public void testCrearPaso() {

        Paso paso = new Paso(1, "Tomar agua");

        assertEquals(1, paso.getNumero());
        assertEquals("Tomar agua", paso.getDescripcion());
        assertFalse(paso.isCompletado());
    }
    
     @Test
    public void testMarcarCompleto() {

        Paso paso = new Paso(1, "Hacer ejercicio");

        paso.marcarCompleto();

        assertTrue(paso.isCompletado());
    }
    
     @Test
    public void testModificarDescripcion() {

        Paso paso = new Paso(1, "Leer");

        paso.setDescripcion("Leer 20 páginas");

        assertEquals(
                "Leer 20 páginas",
                paso.getDescripcion()
        );
    }
    
     @Test
    public void testToString() {

        Paso paso = new Paso(1, "Meditar");

        String resultado = paso.toString();

        assertTrue(resultado.contains("Paso"));
        assertTrue(resultado.contains("Meditar"));
    }
    
    
    
}
