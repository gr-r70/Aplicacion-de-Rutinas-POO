/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author DAVID
 */


public class ModeloAgenda implements IGestionable {

    private ArrayList<Rutina> rutinas;

    public ModeloAgenda() {

        rutinas = new ArrayList <> ();
    }
    @Override
    public void agregar(Rutina r){
        rutinas.add(r);
    }
    @Override
    public void eliminar(int indice){
        if (indice >= 0 && indice < rutinas.size()){
            rutinas.remove(indice);
        }
    }
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
    @Override public ArrayList<Rutina> getRutinas(){
        return rutinas;
    }
    public void ordernarPorNombre(){
        rutinas.sort((r1, r2)->r1.getNombre().compareToIgnoreCase(r2.getNombre()));
    }
    public void ordenarPornNombreDesc(){
        rutinas.sort((r1, r2)->r2.getNombre().compareToIgnoreCase(r1.getNombre()));
    }
    public void ordenarActivasPrimero(){
        rutinas.sort((r1, r2)->{
            if (r1.isActiva()&&!r2.isActiva()) return -1;
            if(!r1.isActiva()&&r2.isActiva())return 1;
            return 0;
        });
    }
    public Rutina buscar (String nombre){
        for (Rutina r: rutinas){
            if(r.getNombre().equalsIgnoreCase(nombre)){
                return r;
            }
        }
        return null;
    }
    public int contarActivas(){
        int count=0;
        for (Rutina r : rutinas){
            if (r.isActiva()) count++;
        }
        return count;
    }
}