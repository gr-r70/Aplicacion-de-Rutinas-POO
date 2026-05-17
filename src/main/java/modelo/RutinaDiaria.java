/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class RutinaDiaria extends Rutina {
    private String horaInicio;
    private String[] diasSemana;
    public RutinaDiaria(String nombre, String horaInicio, String[] diasSemana) {
        super(nombre);
        this.horaInicio = horaInicio;
        this.diasSemana = diasSemana;
    }

    @Override
    public String getTipo() {
        return "Diaria"; 
    }
    public String getDiasSemana(){
        String resultado = "";
        for (String dia: diasSemana){
            resultado += dia + "";
            
        }
        return resultado.trim();
    }
    
    public String getHoraInicio(){
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio){
        this.horaInicio=horaInicio;
    }

    @Override
    public String toString() {
                return super.toString() + " | Hora: " + horaInicio
                + " | Días: " + getDiasSemana();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}