/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase encargada de administrar el conjunto de rutinas
 * registradas en el sistema.
 * 
 * Implementa la interfaz {@code IGestionable}, permitiendo
 * agregar, eliminar, listar, ordenar y buscar rutinas.

 */
public class ModeloAgenda implements IGestionable {

    /**
     * Lista de rutinas almacenadas en el sistema.
     */
    private ArrayList<Rutina> rutinas;

    /**
     * Constructor de la clase ModeloAgenda.
     * Inicializa la lista de rutinas vacía.
     */
    public ModeloAgenda() {
        rutinas = new ArrayList<>();
    }

    /**
     * Agrega una rutina a la lista.
     *
     * @param r rutina que será agregada.
     */
    @Override
    public void agregar(Rutina r) {
        rutinas.add(r);
    }

    /**
     * Elimina una rutina según su índice.
     *
     * @param indice posición de la rutina a eliminar.
     */
    @Override
    public void eliminar(int indice) {

        if (indice >= 0 && indice < rutinas.size()) {
            rutinas.remove(indice);
        }
    }

    /**
     * Genera un listado en texto de las rutinas registradas.
     *
     * @return un {@code String} con las rutinas almacenadas.
     */
    @Override
    public String listar() {

        if (rutinas.isEmpty()) {

            return """
                   
                   NO HAY RUTINAS REGISTRADAS
                   
                   """;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("""
                  
                        LISTA DE RUTINAS
                  
                  """);

        for (int i = 0; i < rutinas.size(); i++) {

            sb.append("[")
              .append(i)
              .append("]\n");

            sb.append(rutinas.get(i))
              .append("\n");

            sb.append("----------------------------------\n");
        }

        return sb.toString();
    }

    /**
     * Obtiene la lista de rutinas.
     *
     * @return lista de rutinas registradas.
     */
    @Override
    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }

    /**
     * Ordena las rutinas alfabéticamente por nombre.
     */
    public void ordernarPorNombre() {

        rutinas.sort(
                (r1, r2)
                -> r1.getNombre()
                        .compareToIgnoreCase(r2.getNombre())
        );
    }

    /**
     * Ordena las rutinas alfabéticamente en orden descendente.
     */
    public void ordenarPornNombreDesc() {

        rutinas.sort(
                (r1, r2)
                -> r2.getNombre()
                        .compareToIgnoreCase(r1.getNombre())
        );
    }

    /**
     * Ordena las rutinas colocando primero
     * las que se encuentran activas.
     */
    public void ordenarActivasPrimero() {

        rutinas.sort((r1, r2) -> {

            if (r1.isActiva() && !r2.isActiva()) {
                return -1;
            }

            if (!r1.isActiva() && r2.isActiva()) {
                return 1;
            }

            return 0;
        });
    }

    /**
     * Busca una rutina por nombre.
     *
     * @param nombre nombre de la rutina a buscar.
     * @return la rutina encontrada o {@code null}
     *         si no existe.
     */
    public Rutina buscar(String nombre) {

        for (Rutina r : rutinas) {

            if (r.getNombre().equalsIgnoreCase(nombre)) {
                return r;
            }
        }

        return null;
    }

    /**
     * Cuenta la cantidad de rutinas activas.
     *
     * @return número de rutinas activas.
     */
    public int contarActivas() {

        int count = 0;

        for (Rutina r : rutinas) {

            if (r.isActiva()) {
                count++;
            }
        }

        return count;
    }
}