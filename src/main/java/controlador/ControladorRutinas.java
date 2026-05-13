/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*;

/**
 *
 * @author DAVID
 */
public class ControladorRutinas {
    
    private ModeloAgenda modelo;
    private int pasoActual;
    
    
    public ControladorRutinas(ModeloAgenda modelo) {
        
        this.modelo = modelo;
        this.pasoActual= 0;
    }
       
    public void agregarRutina(Rutina rutina) {
        modelo.agregar(rutina);
     
    }
    
    public void eliminarRutina(int indice) {
        modelo.eliminar(indice);
    }
    
    public String listarRutinas() {
        
        return modelo.listar();
    }
    public void avanzarPaso(Rutina rutina){
        
        if(pasoActual <rutina.cantidadPasos()-1) {
            pasoActual++;
        }
    }
    
    public void retrocederPaso() {
        if (pasoActual > 0) {
            pasoActual--;
        }
    }
    public Paso obtenerPasoActual(Rutina rutina) {

        return rutina.getPaso(pasoActual);
    }

    public int getPasoActual() {

        return pasoActual;
    }

    public void reiniciarPasos() {

        pasoActual = 0;
    }
    
}
