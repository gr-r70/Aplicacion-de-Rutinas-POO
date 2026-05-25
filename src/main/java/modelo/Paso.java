/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa un paso dentro de una rutina.
 * 
 * Cada paso posee un número identificador,
 * una descripción y un estado de completado.
 * 
 * @author David
 */
public class Paso {

    /**
     * Cantidad máxima de pasos permitidos
     * dentro de una rutina.
     */
    public static final int MAX_PASOS = 10;

    /**
     * Número del paso.
     */
    private int numero;

    /**
     * Descripción del paso.
     */
    private String descripcion;

    /**
     * Estado del paso.
     * {@code true} si está completado.
     */
    private boolean completado;

    /**
     * Constructor de la clase Paso.
     *
     * @param numero número identificador del paso.
     * @param descripcion descripción del paso.
     */
    public Paso(int numero, String descripcion) {

        this.numero = numero;
        this.descripcion = descripcion;
        this.completado = false;
    }

    /**
     * Marca el paso como completado.
     */
    public void marcarCompleto() {
        this.completado = true;
    }

    /**
     * Cambia el estado de completado del paso.
     *
     * @param completado nuevo estado.
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    /**
     * Obtiene el número del paso.
     *
     * @return número del paso.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Modifica el número del paso.
     *
     * @param numero nuevo número.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene la descripción del paso.
     *
     * @return descripción del paso.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del paso.
     *
     * @param descripcion nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Indica si el paso está completado.
     *
     * @return {@code true} si está completado.
     */
    public boolean isCompletado() {
        return completado;
    }

    /**
     * Devuelve una representación textual del paso.
     *
     * @return información formateada del paso.
     */
    @Override
    public String toString() {

        String estado = completado ? "[✓]": "[ ]";

        return estado + "Paso " + numero + ": "+ descripcion;
    }
}