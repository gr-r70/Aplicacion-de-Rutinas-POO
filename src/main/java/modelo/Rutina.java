/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

public abstract class Rutina {
    private static int totalCreadas=0;
    private int id;
    private String nombre;
    private boolean activa;
    private ArrayList<Paso> pasos;

    public Rutina(String nombre) {
        this.nombre = nombre;
        this.activa = true;
        this.pasos = new ArrayList<>();
        totalCreadas++;
    }

    public Rutina(int id, String nombre, boolean activa, ArrayList<Paso> pasos) {
        this.id = id;
        this.nombre = nombre;
        this.activa = activa;
        this.pasos = pasos;
    }
    
    
    
    public abstract String getTipo();
    public void agregarPaso(String descripcion) {
        if (pasos.size() < Paso.MAX_PASOS) {
            pasos.add(new Paso(pasos.size() + 1, descripcion));
        }
    }
    public Paso getPaso(int indice){
        if(indice >= 0 && indice<pasos.size()){
            return pasos.get(indice);
        }
        return null;
    }
    public int cantidadPasos(){
        return pasos.size();
    }

    public static int getTotalCreadas() {
        return totalCreadas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public ArrayList<Paso> getPasos() {
        return pasos;
    }

    @Override
    public String toString() {
        return getTipo() + " | " + nombre + " | Activa: " + activa
                + " | Pasos: " + pasos.size(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
   
}