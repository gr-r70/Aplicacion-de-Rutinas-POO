/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;
import java.time.DayOfWeek;

/**
 * Representa una rutina personalizada con categoría, nivel,
 * hora de inicio y días de ejecución.
 * 
 * Hereda de la clase {@link Rutina} y permite crear rutinas
 * configurables según las necesidades del usuario.
 */
public class RutinaPersonalizada extends Rutina {

    private String categoria;
    private int nivel;
    private LocalTime horaInicio;
    private DayOfWeek[] dias;
    
    /**
     * Construye una nueva rutina personalizada con sus datos básicos.
     *
     * @param nombre Nombre de la rutina.
     * @param categoria Categoría de la rutina.
     * @param nivel Nivel de dificultad de la rutina.
     * @param horaInicio Hora de inicio programada.
     * @param dias Días de la semana en los que se ejecuta la rutina.
     */
    public RutinaPersonalizada(String nombre, String categoria, int nivel, LocalTime horaInicio, DayOfWeek[] dias) {
        super(nombre);
        this.categoria = categoria;
        this.nivel = nivel;
        this.horaInicio = horaInicio;
        this.dias = dias;
    }

    /**
     * Obtiene el tipo de rutina.
     *
     * @return Una cadena con el valor {@code "Personalizada"}.
     */
    @Override
    public String getTipo() {
        return "Personalizada"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    /**
     * Obtiene la categoría de la rutina.
     *
     * @return La categoría asignada a la rutina.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece una nueva categoría para la rutina.
     *
     * @param categoria Nueva categoría de la rutina.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Obtiene el nivel de dificultad de la rutina.
     *
     * @return El nivel de la rutina.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Establece un nuevo nivel de dificultad.
     *
     * @param nivel Nuevo nivel de la rutina.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la hora de inicio de la rutina.
     *
     * @return La hora de inicio programada.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Obtiene los días de la semana asociados a la rutina.
     *
     * @return Un arreglo con los días de ejecución.
     */
    public DayOfWeek[] getDias() {
        return dias;
    }
    
    /**
     * Devuelve una representación en texto de la rutina personalizada.
     *
     * Incluye la información heredada de la clase {@code Rutina},
     * junto con la categoría, nivel y hora de inicio.
     *
     * @return Una cadena con la información de la rutina.
     */
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
