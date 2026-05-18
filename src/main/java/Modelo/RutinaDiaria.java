/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class RutinaDiaria extends Rutina {
    private LocalTime horaInicio;
    private DayOfWeek[] diasSemana;

    public RutinaDiaria(LocalTime horaInicio, DayOfWeek[] diasSemana, String nombre) {
        super(nombre);
        this.horaInicio = horaInicio;
        this.diasSemana = diasSemana;
    }
    @Override
    public String getTipo(){
        return "Diaria";
    }
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
    public String getDiasSemana() {
        String resultado = "";
        for (DayOfWeek dia : diasSemana) {
            resultado += dia.toString() + " ";
        }
        return resultado.trim();
    }
    public LocalTime getHoraInicio(){
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio){
        this.horaInicio=horaInicio;
    }
   public DayOfWeek[] getDiasArray() { 
       return diasSemana; }
       @Override
    public String toString() {
        return super.toString() + " | Hora: " + horaInicio+ " | Días: " + getDiasSemana();
    }
    
}