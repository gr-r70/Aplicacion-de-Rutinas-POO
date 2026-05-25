/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clase encargada de establecer la conexión con la base de datos MySQL.
 * Proporciona un método estático para obtener una conexión a la BD
 * utilizada en el sistema de rutinas.
 * 
 * La base de datos utilizada es "reservas_db".
 */
public class ConexionDB {
    /**
     * URL de conexión a la base de datos MySQL.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/reservas_db";
    /**
     * Usuario de acceso a MySQL.
     */
    private static final String USUARIO = "root";
     /**
     * Contraseña de acceso a MySQL.
     */
    private static final String PASSWORD = "";
    
    /**
     * Establece una conexión con la base de datos.
     *
     * @return una conexión activa de tipo {@code Connection},
     *         o {@code null} si ocurre un error.
     */
    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
}
