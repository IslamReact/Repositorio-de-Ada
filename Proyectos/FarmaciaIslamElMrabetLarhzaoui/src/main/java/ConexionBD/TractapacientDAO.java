/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Tractapacient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author islam
 */
public class TractapacientDAO {
    private static final String SQL_SELECT = "SELECT DNI, NumCollegiat, Dates FROM tractaPacient";
    private static final String SQL_INSERT = "INSERT INTO tractaPacient (DNI, NumCollegiat, Dates) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE tractaPacient SET Dates = ? WHERE DNI = ? AND NumCollegiat = ?";
    private static final String SQL_DELETE = "DELETE FROM tractaPacient WHERE DNI = ? AND NumCollegiat = ? AND Dates = ?";
    private static final String SQL_SELECT_PACIENTS = "SELECT tp.DNI AS DNIPacient, tp.Dates AS DataTractament FROM tractaPacient tp WHERE tp.NumCollegiat = ?";
    
    public List<Tractapacient> obtenerTractaPacientes() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Tractapacient> tractaPacientes = new ArrayList<>();

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("DNI");
                int numColegiat = rs.getInt("NumCollegiat");
                Date dates = rs.getDate("Dates");

                Tractapacient tractaPacient = new Tractapacient(dni, numColegiat, dates);
                tractaPacientes.add(tractaPacient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return tractaPacientes;
    }

    public static int insertar(Tractapacient tractaPacient) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, tractaPacient.getDNI());
            stmt.setInt(2, tractaPacient.getNumCollegiat());
            stmt.setDate(3, new java.sql.Date(tractaPacient.getDates().getTime()));
            registro = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registro;
    }

    public int actualizar(Tractapacient tractaPacient) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setDate(1, new java.sql.Date(tractaPacient.getDates().getTime()));
            stmt.setString(2, tractaPacient.getDNI());
            stmt.setInt(3, tractaPacient.getNumCollegiat());
            registro = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registro;
    }

    public int eliminar(Tractapacient tractaPacient) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, tractaPacient.getDNI());
            stmt.setInt(2, tractaPacient.getNumCollegiat());
            stmt.setDate(3, new java.sql.Date(tractaPacient.getDates().getTime()));
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosEliminados;
    }
    
    public List<Tractapacient> obtenerPacientesPorMedico(int numeroColegiat) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Tractapacient> tractaPacients = new ArrayList<>();

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PACIENTS);
            stmt.setInt(1, numeroColegiat);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String DNI = rs.getString("DNIPacient");
                Date Dates = rs.getDate("DataTractament");

                Tractapacient tractaPacient = new Tractapacient(DNI, numeroColegiat, Dates);
                tractaPacients.add(tractaPacient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return tractaPacients;
    }
}
