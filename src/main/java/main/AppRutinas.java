/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import vista.VentanaPrincipalGUI;

public class AppRutinas {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new VentanaPrincipalGUI().setVisible(true);
            }
        });
    }
}