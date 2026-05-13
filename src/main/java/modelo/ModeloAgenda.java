/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author DAVID
 */
public class ModeloAgenda {
    
    private ArrayList<Rutina> rutinas;
    
    public ModeloAgenda() {
        
        rutinas = new ArrayList <> ();
    }
    
    public void agregar (Rutina rutina) {
        
        rutinas.add(rutina);
    }
    
    public void eliminar(int indice) {
        
        if (indice >= 0 && indice < rutinas.size()) {
            rutinas.remove(indice);
        }
    }
    public String listar(){
        String texto="";
        
        for (Rutina rutina : rutinas) {
            
            texto += rutina.toString() + "\n";
        }
        
        return texto;
    }
    
    public ArrayList<Rutina> getRutinas(){
        return rutinas;
    }
}
