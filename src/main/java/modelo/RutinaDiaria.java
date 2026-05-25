/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Representa una rutina diaria que se ejecuta en una hora
 * específica y en determinados días de la semana.
 * 
 * Hereda de la clase {@code Rutina} y permite validar
 * si la rutina debe ejecutarse según la fecha y hora actual.

 */
public class RutinaDiaria extends Rutina {
    private LocalTime horaInicio;
    private DayOfWeek[] diasSemana;
    
    /**
     * Constructor que crea una rutina diaria con hora,
     * días de ejecución y nombre.
     *
     * @param horaInicio hora en la que inicia la rutina
     * @param diasSemana arreglo de días de la semana en los
     * que se ejecutará la rutina
     * @param nombre nombre de la rutina
     */
    public RutinaDiaria(LocalTime horaInicio, DayOfWeek[] diasSemana, String nombre) {
        super(nombre);
        this.horaInicio = horaInicio;
        this.diasSemana = diasSemana;
    }
    
    /**
     * Obtiene el tipo de rutina.
     *
     * @return una cadena con el valor {@code "Diaria"}
     */
    @Override
    public String getTipo(){
        return "Diaria";
    }
    
    /**
     * Verifica si la rutina debe ejecutarse en el momento actual.
     * 
     * Comprueba si la hora y el día actual coinciden con
     * la programación establecida de la rutina.
     *
     * @return {@code true} si la rutina debe ejecutarse ahora,
     * {@code false} en caso contrario
     */
    public boolean debeEjecutarseAhora(){
        LocalTime ahora=LocalTime.now();
        DayOfWeek hoy = LocalDate.now().getDayOfWeek();
        boolean horaCoincide = ahora.getHour()== horaInicio.getHour()&&ahora.getMinute()==horaInicio.getMinute();
        boolean diaCoincide=false;
        for (DayOfWeek dia : diasSemana){
            if(dia==hoy){
                diaCoincide=true;
                break;
            }
        }
        return horaCoincide && diaCoincide;
    }
    
    /**
     * Obtiene los días de ejecución de la rutina
     * en formato de texto.
     *
     * @return una cadena con los días de la semana
     * separados por espacios
     */
    public String getDiasSemana() {
        String resultado = "";
        for (DayOfWeek dia : diasSemana) {
            resultado += dia.toString() + " ";
        }
        return resultado.trim();
    }
    
    /**
     * Obtiene la hora de inicio de la rutina.
     *
     * @return la hora de inicio configurada
     */
    public LocalTime getHoraInicio(){
        return horaInicio;
    }
    
    /**
     * Modifica la hora de inicio de la rutina.
     *
     * @param horaInicio nueva hora de inicio
     */
    public void setHoraInicio(LocalTime horaInicio){
        this.horaInicio=horaInicio;
    }
    
   /**
     * Obtiene el arreglo de días de la semana
     * configurados para la rutina.
     *
     * @return arreglo de días de ejecución
     */
   public DayOfWeek[] getDiasArray() { 
       return diasSemana; }
   
   /**
     * Obtiene los días de ejecución de la rutina.
     *
     * @return arreglo de días de la semana
     */
   public DayOfWeek[] getDias() {
      return diasSemana;
    }
   
    /**
     * Devuelve una representación en texto
     * de la rutina diaria.
     *
     * @return una cadena con la información
     * de la rutina, incluyendo hora y días
     */
    @Override
    public String toString() {
         return super.toString()
            + """
              
              Hora Inicio: %s
              Días: %s
              """.formatted(
                    horaInicio,
                    getDiasSemana()
            ); }

}