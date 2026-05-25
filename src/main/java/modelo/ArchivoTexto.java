/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;

/**
 *
 * @author crstc
 */
public class ArchivoTexto {
    
    public static void guardarRutinaTXT(Rutina rutina) {

        try (FileWriter fw = new FileWriter("rutinas.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("==============");

            pw.println("ID: " + rutina.getId());
            pw.println("Nombre: " + rutina.getNombre());
            pw.println("Tipo: " + rutina.getTipo());

            if (rutina instanceof RutinaDiaria rd) {

                pw.println("Hora: " + rd.getHoraInicio());

                String dias = "";

                for (DayOfWeek d : rd.getDias()) {
                    dias += d + " ";
                }

                pw.println("Dias: " + dias);

            } else if (rutina instanceof RutinaPersonalizada rp) {

                pw.println("Categoria: " + rp.getCategoria());
                pw.println("Nivel: " + rp.getNivel());
                pw.println("Hora: " + rp.getHoraInicio());

                String dias = "";

                for (DayOfWeek d : rp.getDias()) {
                    dias += d + " ";
                }

                pw.println("Dias: " + dias);
            }

            pw.println("==============");

        } catch (IOException e) {

            System.out.println(
                    "Error guardando TXT: " + e.getMessage()
            );
        }
    }
    
}
