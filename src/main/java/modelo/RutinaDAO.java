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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RutinaDAO {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public boolean guardarRutina(Rutina rutina) {
        String sql = "INSERT INTO rutinas (nombre, activa, tipo, hora_inicio, dias_semana, categoria, nivel) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, rutina.getNombre());
            pstmt.setBoolean(2, rutina.isActiva());
            pstmt.setString(3, rutina.getTipo());

            if (rutina instanceof RutinaDiaria rd) {

                String horaFormateada = rd.getHoraInicio().format(TIME_FORMATTER);
                pstmt.setString(4, horaFormateada);

                pstmt.setString(5, diasArrayToString(rd.getDiasArray()));
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            } else if (rutina instanceof RutinaPersonalizada rp) {
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

            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        rutina.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Error en la BD (Guardar): " + e.getMessage());
            return false;
        }
    }

    public boolean guardarPaso(int rutinaId, Paso paso) {

        String sql = """
        INSERT INTO pasos
        (rutina_id, numero, descripcion, completado)
        VALUES (?, ?, ?, ?)
    """;

        try (
                Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rutinaId);
            pstmt.setInt(2, paso.getNumero());
            pstmt.setString(3, paso.getDescripcion());
            pstmt.setBoolean(4, paso.isCompletado());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error guardando paso: "
                    + e.getMessage()
            );

            return false;
        }
    }

    private void guardarPasos(Rutina rutina) {
        System.out.println(
                "Pasos a guardar: "
                + rutina.getPasos().size()
        );

        String sql = """
        INSERT INTO pasos
        (rutina_id, numero, descripcion, completado)
        VALUES (?, ?, ?, ?)
    """;

        try (
                Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Paso paso : rutina.getPasos()) {

                pstmt.setInt(1, rutina.getId());
                pstmt.setInt(2, paso.getNumero());
                pstmt.setString(3, paso.getDescripcion());
                pstmt.setBoolean(4, paso.isCompletado());

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error guardando pasos: " + e.getMessage()
            );
        }
    }

    public List<Rutina> obtenerRutinas() {
        List<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutinas";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                boolean activa = rs.getBoolean("activa");
                String tipo = rs.getString("tipo");
                Rutina rutina = null;

                if ("Diaria".equals(tipo)) {
                    String horaStr = rs.getString("hora_inicio");

                    LocalTime horaInicio = (horaStr != null) ? LocalTime.parse(horaStr) : LocalTime.NOON;
                    DayOfWeek[] dias = stringToDiasArray(rs.getString("dias_semana"));
                    rutina = new RutinaDiaria(horaInicio, dias, nombre);
                } else if ("Personalizada".equals(tipo)) {
                    String categoria = rs.getString("categoria");
                    int nivel = rs.getInt("nivel");
                    rutina = new RutinaPersonalizada(nombre, categoria, nivel);
                }

                if (rutina != null) {
                    rutina.setId(id);
                    rutina.setActiva(activa);
                    rutina.setPasos(obtenerPasos(id));
                    lista.add(rutina);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean editarRutina(int id, Rutina rutina) {
        String sql = "UPDATE rutinas SET nombre=?, activa=?, tipo=?, "
                + "hora_inicio=?, dias_semana=?, categoria=?, nivel=? "
                + "WHERE id=?";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rutina.getNombre());
            pstmt.setBoolean(2, rutina.isActiva());
            pstmt.setString(3, rutina.getTipo());

            if (rutina instanceof RutinaDiaria rd) {
                pstmt.setString(4, rd.getHoraInicio().format(TIME_FORMATTER));
                pstmt.setString(5, diasArrayToString(rd.getDiasArray()));
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            } else if (rutina instanceof RutinaPersonalizada rp) {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
                pstmt.setString(6, rp.getCategoria());
                pstmt.setInt(7, rp.getNivel());
            }

            pstmt.setInt(8, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarRutina(int id) {
        String sql = "DELETE FROM rutinas WHERE id=?";
        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    private ArrayList<Paso> obtenerPasos(int rutinaId) {

        ArrayList<Paso> pasos = new ArrayList<>();

        String sql = """
        SELECT numero, descripcion, completado
        FROM pasos
        WHERE rutina_id = ?
    """;

        try (
                Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rutinaId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Paso p = new Paso(
                        rs.getInt("numero"),
                        rs.getString("descripcion"));

                p.setCompletado(rs.getBoolean("completado"));

                pasos.add(p);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error obteniendo pasos: " + e.getMessage());
        }

        return pasos;
    }

    private String diasArrayToString(DayOfWeek[] dias) {
        if (dias == null || dias.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dias.length; i++) {
            sb.append(dias[i].name());
            if (i < dias.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private DayOfWeek[] stringToDiasArray(String diasStr) {
        if (diasStr == null || diasStr.isEmpty()) {
            return new DayOfWeek[]{};
        }
        String[] partes = diasStr.split(",");
        DayOfWeek[] dias = new DayOfWeek[partes.length];
        for (int i = 0; i < partes.length; i++) {
            try {
                dias[i] = DayOfWeek.valueOf(partes[i].trim());
            } catch (IllegalArgumentException e) {
                dias[i] = DayOfWeek.MONDAY;
            }
        }
        return dias;
    }
}
