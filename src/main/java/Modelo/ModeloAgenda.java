/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author crstc
 */
public class ModeloAgenda implements IGestionable {
    private ArrayList<Rutina> rutinas;
    public ModeloAgenda(){
        this.rutinas= new ArrayList<>();
    }

    @Override
    public void agregar(Rutina r) {
        rutinas.add(r); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    @Override
    public void eliminar (int indice){
        if (indice>=0 && indice < rutinas.size()){
            rutinas.remove(indice);
        }
    }
    @Override
    public String lista(){
        if (rutinas.isEmpty()){
            return "No hay rutinas registradas";
        }
        String resultado = "";
        for (Rutina r: rutinas){
            resultado += r.toString()+"\n";
        }
        return resultado;
    }
    public void ordenarPorNombre(){
        rutinas.sort((r1, r2)-> r1.getNombre().compareToIgnoreCase(r2.getNombre()));
    }
    public void ordenarPorDesc(){
        rutinas.sort((r1, r2) -> r2.getNombre().compareToIgnoreCase(r1.getNombre()));
    }
    public Rutina buscar(String nombre){
        for(Rutina r:rutinas){
            if(r.getNombre().equalsIgnoreCase(nombre)){
                return r;
            }
        }
        return null;
    }
    public int contarActivas(){
        int count =0;
        for(Rutina r: rutinas){
            if (r.isActiva())count++;
        }
        return count;
    }
    @Override
public ArrayList<Rutina> getRutinas() {
    return rutinas;
}
    
}
