/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

public abstract class Rutina {
    private static int totalCreadas = 0;
    private int id;
    private String nombre;
    private boolean activa;
    private ArrayList<Paso> pasos;
    private int indicePasoActual = 0;

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

    public int getIndicePasoActual() {
        return indicePasoActual;
    }
    public void setIndicePasoActual(int indicePasoActual) {
        this.indicePasoActual = indicePasoActual;
    }

    public ArrayList<Paso> getPasos() {
        return pasos;
    }
    public void setPasos(ArrayList<Paso> pasos) {
        this.pasos = pasos;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    // --------------------------------------------------------------

    @Override
    public String toString() {
        return """
               Tipo: %s
               Nombre: %s
               Estado: %s
               Pasos: %d
               """.formatted(getTipo(), nombre, activa ? "Activa" : "Inactiva", pasos.size());
    }
}