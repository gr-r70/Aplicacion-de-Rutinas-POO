/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import vista.VentanaPrincipal;

public class AppRutinas {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal();
        });
    }
}