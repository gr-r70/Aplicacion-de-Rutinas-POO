/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ModeloAgenda;
import modelo.Rutina;
import modelo.RutinaDiaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorRutinasTest {

    private ModeloAgenda modelo;
    private ControladorRutinas controlador;

    @BeforeEach
    public void inicializar() {

        modelo = new ModeloAgenda();

        controlador = new ControladorRutinas(modelo);
    }

    @Test
    public void testAgregarRutina() {

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(8, 0),
                new DayOfWeek[]{DayOfWeek.MONDAY},
                "Rutina Mañana"
        );

        boolean resultado =
                controlador.agregarRutina(rutina);

        assertTrue(resultado);

        assertEquals(
                1,
                modelo.getRutinas().size()
        );
    }

    @Test
    public void testEliminarRutina() {

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(9, 0),
                new DayOfWeek[]{DayOfWeek.TUESDAY},
                "Rutina Gym"
        );

        controlador.agregarRutina(rutina);

        boolean eliminado =
                controlador.eliminarRutina(0);

        assertTrue(eliminado);

        assertEquals(
                0,
                modelo.getRutinas().size()
        );
    }

    @Test
    public void testAvanzarPaso() {

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(10, 0),
                new DayOfWeek[]{DayOfWeek.WEDNESDAY},
                "Rutina Estudio"
        );

        rutina.agregarPaso("Abrir cuaderno");
        rutina.agregarPaso("Leer teoría");

        controlador.avanzarPaso(rutina);

        assertEquals(
                1,
                rutina.getIndicePasoActual()
        );
    }

    @Test
    public void testRetrocederPaso() {

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(11, 0),
                new DayOfWeek[]{DayOfWeek.THURSDAY},
                "Rutina Lectura"
        );

        rutina.agregarPaso("Paso 1");
        rutina.agregarPaso("Paso 2");

        rutina.setIndicePasoActual(1);

        controlador.retrocederPaso(rutina);

        assertEquals(
                0,
                rutina.getIndicePasoActual()
        );
    }

    @Test
    public void testBuscarRutina() {

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(7, 30),
                new DayOfWeek[]{DayOfWeek.FRIDAY},
                "Rutina Trabajo"
        );

        controlador.agregarRutina(rutina);

        Rutina encontrada =
                modelo.buscar("Rutina Trabajo");

        assertNotNull(encontrada);

        assertEquals(
                "Rutina Trabajo",
                encontrada.getNombre()
        );
    }

    @Test
    public void testContarActivas() {

        Rutina r1 = new RutinaDiaria(
                LocalTime.of(6, 0),
                new DayOfWeek[]{DayOfWeek.MONDAY},
                "Rutina 1"
        );

        Rutina r2 = new RutinaDiaria(
                LocalTime.of(7, 0),
                new DayOfWeek[]{DayOfWeek.TUESDAY},
                "Rutina 2"
        );

        r2.setActiva(false);

        controlador.agregarRutina(r1);
        controlador.agregarRutina(r2);

        assertEquals(
                1,
                modelo.contarActivas()
        );
    }
}