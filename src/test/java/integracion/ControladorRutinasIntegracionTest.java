/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integracion;

import controlador.ControladorRutinas;
import modelo.ModeloAgenda;
import modelo.Rutina;
import modelo.RutinaDAO;
import modelo.RutinaDiaria;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorRutinasIntegracionTest {

    @Test
    public void testGuardarYConsultarRutinaEnBD() {

        // Arrange
        ModeloAgenda modelo = new ModeloAgenda();
        ControladorRutinas controlador = new ControladorRutinas(modelo);

        Rutina rutina = new RutinaDiaria(
                LocalTime.of(8, 0),
                new DayOfWeek[]{DayOfWeek.MONDAY},
                "Rutina Integracion"
        );

        // Act
        boolean resultado = controlador.agregarRutina(rutina);

        // Assert
        assertTrue(resultado);

        // Consultar directamente desde la BD
        RutinaDAO dao = new RutinaDAO();

        List<Rutina> rutinas = dao.obtenerRutinas();

        boolean encontrada = false;

        for (Rutina r : rutinas) {

            if (r.getNombre().equals("Rutina Integracion")) {
                encontrada = true;
                break;
            }
        }

        assertTrue(encontrada);
    }
}