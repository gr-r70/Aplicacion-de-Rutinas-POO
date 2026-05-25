/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;
import java.time.DayOfWeek;

public class RutinaPersonalizada extends Rutina {

    private String categoria;
    private int nivel;
    private LocalTime horaInicio;
    private DayOfWeek[] dias;

public RutinaPersonalizada(String nombre, String categoria, int nivel, LocalTime horaInicio, DayOfWeek[] dias) {
    super(nombre);
    this.categoria = categoria;
    this.nivel = nivel;
    this.horaInicio = horaInicio;
    this.dias = dias;
}
    @Override
    public String getTipo() {
        return "Personalizada"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public DayOfWeek[] getDias() {
        return dias;
    }

    @Override
    public String toString() {

        return super.toString()
                + """
          
          Categoría: %s
          Nivel: %d
          Hora Inicio: %s
          """.formatted(categoria,nivel,horaInicio);
    }

}
