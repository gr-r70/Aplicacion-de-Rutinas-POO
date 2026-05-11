/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

  
public class Paso {

    public static final int MAX_PASOS=10;
    private int numero;
    private String descripcion;
    private boolean completado;

    public Paso(int numero, String descripcion) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.completado = false; }

    public void marcarCompleto(){
        this.completado=true;
        }

    public int getNumero() {
            return numero;
        }

    public void setNumero(int numero) {
            this.numero = numero;
        }

    public String getDescripcion() {
            return descripcion;
        }

    public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

    public boolean isCompletado() {
            return completado;
        }

    @Override
    public String toString() {
            String estado = completado ?"[✓]" : "[ ]";
            return estado + "Paso" +numero +":"+ descripcion;//Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

}
    

