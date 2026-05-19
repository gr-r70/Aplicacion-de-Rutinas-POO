/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*;

/**
 *
 * @author DAVID
 */
public class ControladorRutinas {
    
    private ModeloAgenda modelo;
    private int pasoActual;
    
    private ServicioTTS servicioTTS;
    private ServicioRecordatorio servicioRecordatorio;
    private RutinaDAO rutinaDAO;
    
    
    public ControladorRutinas(ModeloAgenda modelo) {
        
        this.modelo = modelo;
        this.pasoActual= 0;
        
        servicioTTS = new ServicioTTS("es");
        servicioRecordatorio = new ServicioRecordatorio(modelo, servicioTTS);
        rutinaDAO = new RutinaDAO();
    }
    // CRUD
       
    public void agregarRutina(Rutina rutina) {
        
        modelo.agregar(rutina);
        rutinaDAO.guardarRutina(rutina);
     
    }
    
    public void eliminarRutina(int indice) {
        
        modelo.eliminar(indice);
    }
    
    public String listarRutinas() {
        
        return modelo.listar();
    }
    //Control de pasos 
    public void avanzarPaso(Rutina rutina){
        
        if(pasoActual <rutina.cantidadPasos()-1) {
            pasoActual++;
        }
    }
    
    public void retrocederPaso() {
        if (pasoActual > 0) {
            pasoActual--;
        }
    }
    public Paso obtenerPasoActual(Rutina rutina) {

        return rutina.getPaso(pasoActual);
    }

    public int getPasoActual() {

        return pasoActual;
    }

    public void reiniciarPasos() {

        pasoActual = 0;
    }
    //Texto a voz 
    
    public void leerPasoActual(Rutina rutina) {
        
        Paso paso = obtenerPasoActual(rutina);
        if (paso != null) {
            servicioTTS.leerTexto(paso.getDescripcion());
        }
    }
    
    public void leerRutina(Rutina rutina) {
        
        servicioTTS.leerTexto("iniciando rutina"+rutina.getNombre());
    }
    public void detenerLectura(){
        
        servicioTTS.detener();
    }
    
    //Recordatorios
    
    public void iniciarRecordatorios(){
        
        servicioRecordatorio.iniciar();
        
    }
    
    public void detenerRecordatorios(){
        
        servicioRecordatorio.detener ();
    }
    
    //busqueda y ordenamiento 
    
    public Rutina buscarRutina(String nombre) {

        return modelo.buscar(nombre);
    }
    
    public void ordenarPornombre() {
        
        modelo.ordernarPorNombre(); 
    }
    
     public void ordenarPorNombreDesc() {

        modelo.ordenarPornNombreDesc();
    }
    
    public void ordenarActivasPrimero() {
        
        modelo.ordenarActivasPrimero();
    }
    
      public int contarRutinasActivas() {

        return modelo.contarActivas();
        
      }
    
}
