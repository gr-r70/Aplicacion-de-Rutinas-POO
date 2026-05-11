/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
public interface IGestionable{
    void agregar(Rutina r);
    void eliminar (int indice);
    ArrayList<Rutina> getRutinas();
    String lista();
}

