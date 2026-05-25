/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package controlador;

import modelo.*;
import java.util.List;

/**
 * Controlador encargado de gestionar la lógica
 * entre el modelo de datos y la persistencia
 * de las rutinas.
 * 
 * Permite cargar, agregar, eliminar y gestionar
 * la navegación de pasos dentro de las rutinas.
 * 
 * Sigue el patrón MVC actuando como intermediario
 * entre la vista y el modelo.
 */
public class ControladorRutinas {

    private ModeloAgenda modelo;
    private RutinaDAO dao;
    
    /**
     * Construye un controlador asociado
     * a un modelo de agenda.
     *
     * @param modelo Modelo que administra
     * las rutinas de la aplicación.
     */
    public ControladorRutinas(ModeloAgenda modelo) {
        this.modelo = modelo;
        this.dao = new RutinaDAO();
    }

    /**
     * Carga las rutinas almacenadas en la base de datos.
     * 
     * Elimina las rutinas actuales del modelo y
     * reemplaza la información con los datos obtenidos
     * desde la persistencia.
     */
    public void cargarDesdeBD() {
        List<Rutina> listaBD = dao.obtenerRutinas();
        modelo.getRutinas().clear();
        for (Rutina r : listaBD) {
            modelo.agregar(r);
        }
    }

    /**
     * Agrega una nueva rutina al sistema.
     * 
     * La rutina se almacena primero en la base de datos
     * y, si el proceso es exitoso, se agrega al modelo.
     *
     * @param r Rutina que será agregada.
     * @return {@code true} si la rutina fue almacenada
     * correctamente, {@code false} en caso contrario.
     */
    public boolean agregarRutina(Rutina r) {
        if (dao.guardarRutina(r)) {
            modelo.agregar(r);
            return true;
        }
        return false;
    }

    /**
     * Elimina una rutina seleccionada del sistema.
     * 
     * Primero elimina la rutina de la base de datos
     * y posteriormente del modelo si la operación
     * fue exitosa.
     *
     * @param filaSeleccionada Índice de la rutina
     * seleccionada en la lista.
     * @return {@code true} si la rutina fue eliminada
     * correctamente, {@code false} en caso contrario.
     */
    public boolean eliminarRutina(int filaSeleccionada) {
        if (filaSeleccionada >= 0 && filaSeleccionada < modelo.getRutinas().size()) {
            Rutina r = modelo.getRutinas().get(filaSeleccionada);
            if (dao.eliminarRutina(r.getId())) {
                modelo.eliminar(filaSeleccionada);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Avanza al siguiente paso de una rutina.
     * 
     * El índice del paso actual se incrementa
     * siempre que no se haya alcanzado el último paso.
     *
     * @param r Rutina sobre la cual se avanzará
     * al siguiente paso.
     */
    public void avanzarPaso(Rutina r) {
        if (r != null && r.getIndicePasoActual() < r.getPasos().size() - 1) {
            r.setIndicePasoActual(r.getIndicePasoActual() + 1);
        }
    }
    
    /**
     * Retrocede al paso anterior de una rutina.
     * 
     * El índice del paso actual se decrementa
     * siempre que no se encuentre en el primer paso.
     *
     * @param r Rutina sobre la cual se retrocederá
     * al paso anterior.
     */
    public void retrocederPaso(Rutina r) {
        if (r != null && r.getIndicePasoActual() > 0) {
            r.setIndicePasoActual(r.getIndicePasoActual() - 1);
        }
    }
}