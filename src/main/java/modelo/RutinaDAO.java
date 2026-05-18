/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja tanto RutinaDiaria como RutinaPersonalizada con una sola tabla.
 */
public class RutinaDAO {


    // GUARDAR
    public boolean guardarRutina(Rutina rutina) {
        String sql = "INSERT INTO rutinas (nombre, activa, tipo, hora_inicio, dias_semana, categoria, nivel) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rutina.getNombre());
            pstmt.setBoolean(2, rutina.isActiva());
            pstmt.setString(3, rutina.getTipo());

            if (rutina instanceof RutinaDiaria) {
                RutinaDiaria rd = (RutinaDiaria) rutina;
                pstmt.setString(4, rd.getHoraInicio().toString());
                pstmt.setString(5, diasArrayToString(rd.getDiasArray()));
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);

            } else if (rutina instanceof RutinaPersonalizada) {
                RutinaPersonalizada rp = (RutinaPersonalizada) rutina;
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
                pstmt.setString(6, rp.getCategoria());
                pstmt.setInt(7, rp.getNivel());

            } else {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error en la BD: " + e.getMessage());
            return false;
        }
    }

    // MOSTRAR TODO
    public List<Rutina> obtenerRutinas() {
        List<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutinas";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nombre  = rs.getString("nombre");
                boolean activa = rs.getBoolean("activa");
                String tipo    = rs.getString("tipo");
                Rutina rutina  = null;

                if ("Diaria".equals(tipo)) {
                    // "HH:mm" a LocalTime
                    LocalTime horaInicio = LocalTime.parse(rs.getString("hora_inicio"));
                    // "MONDAY,WEDNESDAY" a DayOfWeek[]
                    DayOfWeek[] dias = stringToDiasArray(rs.getString("dias_semana"));
                    rutina = new RutinaDiaria(horaInicio, dias, nombre);

                } else if ("Personalizada".equals(tipo)) {
                    String categoria = rs.getString("categoria");
                    int nivel        = rs.getInt("nivel");
                    rutina = new RutinaPersonalizada(nombre, categoria, nivel);
                }

                if (rutina != null) {
                    rutina.setActiva(activa);
                    lista.add(rutina);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    
    // EDITA
    public boolean editarRutina(int id, Rutina rutina) {
        String sql = "UPDATE rutinas SET nombre=?, activa=?, tipo=?, "
                   + "hora_inicio=?, dias_semana=?, categoria=?, nivel=? "
                   + "WHERE id=?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rutina.getNombre());
            pstmt.setBoolean(2, rutina.isActiva());
            pstmt.setString(3, rutina.getTipo());

            if (rutina instanceof RutinaDiaria) {
                RutinaDiaria rd = (RutinaDiaria) rutina;
                pstmt.setString(4, rd.getHoraInicio().toString());
                pstmt.setString(5, diasArrayToString(rd.getDiasArray()));
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);

            } else if (rutina instanceof RutinaPersonalizada) {
                RutinaPersonalizada rp = (RutinaPersonalizada) rutina;
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
                pstmt.setString(6, rp.getCategoria());
                pstmt.setInt(7, rp.getNivel());

            } else {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            pstmt.setInt(8, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
            return false;
        }
    }

    
    // ELIMINAR
    public boolean eliminarRutina(int id) {
        String sql = "DELETE FROM rutinas WHERE id=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte un arreglo de DayOfWeek a String separado por comas.
     * Ejemplo: [MONDAY, FRIDAY] → "MONDAY,FRIDAY"
     */
    private String diasArrayToString(DayOfWeek[] dias) {
        if (dias == null || dias.length == 0) return "";
        String resultado = "";
        for (int i = 0; i < dias.length; i++) {
            resultado += dias[i].toString();
            if (i < dias.length - 1) resultado += ",";
        }
        return resultado;
    }

    /**
     * Convierte un String separado por comas a un arreglo de DayOfWeek.
     * Ejemplo: "MONDAY,FRIDAY" → [MONDAY, FRIDAY]
     */
    private DayOfWeek[] stringToDiasArray(String diasStr) {
        if (diasStr == null || diasStr.isEmpty()) return new DayOfWeek[]{};
        String[] partes = diasStr.split(",");
        DayOfWeek[] dias = new DayOfWeek[partes.length];
        for (int i = 0; i < partes.length; i++) {
            dias[i] = DayOfWeek.valueOf(partes[i].trim());
        }
        return dias;
    }
}