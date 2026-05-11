/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class RutinaPersonalizada extends Rutina {
    private String categoria;
    private int nivel;
        public RutinaPersonalizada(String nombre, String categoria, int nivel) {
        super(nombre);
        this.categoria = categoria;
        this.nivel = nivel;
    }

    @Override
    public String getTipo() {
        return "Personalizada"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return super.toString() + " | Categoria: " + categoria
                + " | Nivel: " + nivel; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
   
}