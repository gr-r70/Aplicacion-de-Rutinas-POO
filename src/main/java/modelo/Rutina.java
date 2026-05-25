/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 * Clase abstracta que representa una rutina dentro del sistema.
 * 
 * Una rutina contiene información básica como identificador,
 * nombre, estado de activación y una lista de pasos asociados.
 * También permite gestionar el paso actual de ejecución.
 * 
 * Esta clase sirve como base para los diferentes tipos de rutinas,
 * como {@code RutinaDiaria} y {@code RutinaPersonalizada}.
 */
public abstract class Rutina {

    /**
     * Contador total de rutinas creadas.
     */
    private static int totalCreadas = 0;

    /**
     * Identificador único de la rutina.
     */
    private int id;

    /**
     * Nombre de la rutina.
     */
    private String nombre;

    /**
     * Estado de la rutina.
     * {@code true} si está activa, {@code false} si está inactiva.
     */
    private boolean activa;

    /**
     * Lista de pasos pertenecientes a la rutina.
     */
    private ArrayList<Paso> pasos;

    /**
     * Índice del paso actual de la rutina.
     */
    private int indicePasoActual = 0;

    /**
     * Constructor para crear una rutina nueva.
     * Inicializa la rutina como activa y crea
     * una lista vacía de pasos.
     *
     * @param nombre nombre de la rutina.
     */
    public Rutina(String nombre) {

        this.nombre = nombre;
        this.activa = true;
        this.pasos = new ArrayList<>();

        totalCreadas++;
    }

    /**
     * Constructor para reconstruir una rutina
     * desde la base de datos.
     *
     * @param id identificador de la rutina.
     * @param nombre nombre de la rutina.
     * @param activa estado de la rutina.
     * @param pasos lista de pasos asociados.
     */
    public Rutina(
            int id,
            String nombre,
            boolean activa,
            ArrayList<Paso> pasos
    ) {

        this.id = id;
        this.nombre = nombre;
        this.activa = activa;
        this.pasos = pasos;
    }

    /**
     * Obtiene el tipo específico de rutina.
     * Debe ser implementado por las clases hijas.
     *
     * @return el tipo de rutina.
     */
    public abstract String getTipo();

    /**
     * Agrega un nuevo paso a la rutina,
     * siempre que no se exceda el máximo permitido.
     *
     * @param descripcion descripción del paso.
     */
    public void agregarPaso(String descripcion) {

        if (pasos.size() < Paso.MAX_PASOS) {

            pasos.add(
                    new Paso(
                            pasos.size() + 1,
                            descripcion
                    )
            );
        }
    }

    /**
     * Obtiene el índice del paso actual.
     *
     * @return índice del paso actual.
     */
    public int getIndicePasoActual() {
        return indicePasoActual;
    }

    /**
     * Establece el índice del paso actual.
     *
     * @param indicePasoActual nuevo índice del paso.
     */
    public void setIndicePasoActual(
            int indicePasoActual
    ) {
        this.indicePasoActual = indicePasoActual;
    }

    /**
     * Obtiene la lista de pasos de la rutina.
     *
     * @return lista de pasos.
     */
    public ArrayList<Paso> getPasos() {
        return pasos;
    }

    /**
     * Establece la lista de pasos de la rutina.
     *
     * @param pasos nueva lista de pasos.
     */
    public void setPasos(
            ArrayList<Paso> pasos
    ) {
        this.pasos = pasos;
    }

    /**
     * Obtiene el identificador de la rutina.
     *
     * @return identificador de la rutina.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la rutina.
     *
     * @param id nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la rutina.
     *
     * @return nombre de la rutina.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Verifica si la rutina está activa.
     *
     * @return {@code true} si está activa,
     *         {@code false} si está inactiva.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Cambia el estado de activación de la rutina.
     *
     * @param activa nuevo estado de la rutina.
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    // --------------------------------------------------------------
    /**
     * Genera una representación en texto de la rutina,
     * mostrando tipo, nombre, estado y cantidad de pasos.
     *
     * @return representación textual de la rutina.
     */
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