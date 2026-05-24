/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package controlador;

import modelo.*;
import java.util.List;

public class ControladorRutinas {

    private ModeloAgenda modelo;
    private RutinaDAO dao;

    public ControladorRutinas(ModeloAgenda modelo) {
        this.modelo = modelo;
        this.dao = new RutinaDAO();
    }

    public void cargarDesdeBD() {
        List<Rutina> listaBD = dao.obtenerRutinas();
        modelo.getRutinas().clear();
        for (Rutina r : listaBD) {
            modelo.agregar(r);
        }
    }

    public boolean agregarRutina(Rutina r) {
        if (dao.guardarRutina(r)) {
            modelo.agregar(r);
            return true;
        }
        return false;
    }

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

    public void avanzarPaso(Rutina r) {
        if (r != null && r.getIndicePasoActual() < r.getPasos().size() - 1) {
            r.setIndicePasoActual(r.getIndicePasoActual() + 1);
        }
    }

    public void retrocederPaso(Rutina r) {
        if (r != null && r.getIndicePasoActual() > 0) {
            r.setIndicePasoActual(r.getIndicePasoActual() - 1);
        }
    }
}