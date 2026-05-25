/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import vista.VentanaPrincipalGUI;

/**
 * Clase principal de la aplicación.
 * 
 * Se encarga de iniciar la ejecución del sistema
 * y mostrar la ventana principal de la interfaz gráfica.
 */
public class AppRutinas {
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * Ejecuta la interfaz gráfica en el hilo de eventos
     * de Swing para garantizar una correcta gestión
     * de la interfaz de usuario.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new VentanaPrincipalGUI().setVisible(true);
            }
        });
    }
}