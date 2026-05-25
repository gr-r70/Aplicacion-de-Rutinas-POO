/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 * Interfaz que define las operaciones básicas de gestión
 * para administrar rutinas dentro del sistema.
 * 
 * Permite agregar, eliminar, obtener y listar rutinas.
 */
public interface IGestionable {

    /**
     * Agrega una nueva rutina al sistema.
     *
     * @param r rutina que será agregada.
     */
    void agregar(Rutina r);

    /**
     * Elimina una rutina según su posición en la colección.
     *
     * @param indice posición de la rutina a eliminar.
     */
    void eliminar(int indice);

    /**
     * Obtiene la lista completa de rutinas registradas.
     *
     * @return una lista de objetos {@code Rutina}.
     */
    ArrayList<Rutina> getRutinas();

    /**
     * Genera una representación en texto de las rutinas
     * registradas en el sistema.
     *
     * @return un {@code String} con el listado de rutinas.
     */
    String listar();
}
