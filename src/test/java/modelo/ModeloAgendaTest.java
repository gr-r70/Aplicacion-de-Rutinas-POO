/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author DAVID
 */
import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModeloAgendaTest {
     @Test
    public void testAgregarRutina() {

        ModeloAgenda agenda = new ModeloAgenda();

        Rutina rutina = new RutinaPersonalizada(
                "Ejercicio",
                "Salud",
                3
        );

        agenda.agregar(rutina);

        assertEquals(1, agenda.getRutinas().size());
    }
    
     @Test
    public void testEliminarRutina() {

        ModeloAgenda agenda = new ModeloAgenda();

        Rutina rutina = new RutinaPersonalizada(
                "Lectura",
                "Estudio",
                2
        );

        agenda.agregar(rutina);

        agenda.eliminar(0);

        assertEquals(0, agenda.getRutinas().size());
    }
    
     @Test
    public void testBuscarRutina() {

        ModeloAgenda agenda = new ModeloAgenda();

        Rutina rutina = new RutinaPersonalizada(
                "Meditación",
                "Bienestar",
                1
        );

        agenda.agregar(rutina);

        Rutina encontrada = agenda.buscar("Meditación");

        assertNotNull(encontrada);
        assertEquals("Meditación", encontrada.getNombre());
    }
    
    
    @Test
    public void testContarRutinasActivas() {

        ModeloAgenda agenda = new ModeloAgenda();

        Rutina r1 = new RutinaPersonalizada(
                "Yoga",
                "Salud",
                2
        );

        Rutina r2 = new RutinaPersonalizada(
                "Programar",
                "Estudio",
                5
        );

        r2.setActiva(false);

        agenda.agregar(r1);
        agenda.agregar(r2);

        assertEquals(1, agenda.contarActivas());
    }
    
    
     @Test
    public void testOrdenarPorNombre() {

        ModeloAgenda agenda = new ModeloAgenda();

        Rutina r1 = new RutinaPersonalizada(
                "Zumba",
                "Ejercicio",
                2
        );

        Rutina r2 = new RutinaPersonalizada(
                "Ajedrez",
                "Mental",
                1
        );

        agenda.agregar(r1);
        agenda.agregar(r2);

        agenda.ordernarPorNombre();

        assertEquals(
                "Ajedrez",
                agenda.getRutinas().get(0).getNombre()
        );
    }
    
     @Test
    public void testRutinaDiaria() {

        DayOfWeek[] dias = {
            DayOfWeek.MONDAY,
            DayOfWeek.FRIDAY
        };

        RutinaDiaria rutina = new RutinaDiaria(
                LocalTime.of(8, 0),
                dias,
                "Despertar"
        );

        assertEquals("Diaria", rutina.getTipo());
        assertEquals("Despertar", rutina.getNombre());
    }
    
}
