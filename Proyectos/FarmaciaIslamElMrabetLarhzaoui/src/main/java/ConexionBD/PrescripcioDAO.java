/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Prescriu;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author islam
 */
public class PrescripcioDAO {
    private static final String SQL_SELECT = "SELECT DNI, NumCollegiat, NomComercial, Dates, Quantitat FROM prescriu";
    private static final String SQL_INSERT = "INSERT INTO prescriu (DNI, NumCollegiat, NomComercial, Dates, Quantitat) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE prescriu SET Quantitat = ? WHERE DNI = ? AND NumCollegiat = ? AND NomComercial = ? AND Dates = ?";
    private static final String SQL_DELETE = "DELETE FROM prescriu WHERE DNI = ? AND NumCollegiat = ? AND NomComercial = ? AND Dates = ?";

    public List<Prescriu> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Prescriu> prescrius = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String DNI = rs.getString("DNI");
                int numCollegiat = rs.getInt("NumCollegiat");
                String nomComercial = rs.getString("NomComercial");
                java.sql.Date dates = rs.getDate("Dates");
                int quantitat = rs.getInt("Quantitat");

                Prescriu prescriu = new Prescriu(DNI, numCollegiat, nomComercial, dates, quantitat);
                prescrius.add(prescriu);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return prescrius;
    }
    

    public static int insertar(Prescriu prescriu) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosInsertados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, prescriu.getDNI());
            stmt.setInt(2, prescriu.getNumCollegiat());
            stmt.setString(3, prescriu.getNomComercial());
            stmt.setDate(4, (Date) prescriu.getDates());
            stmt.setInt(5, prescriu.getQuantitat());

            registrosInsertados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosInsertados;
    }

    public int actualizar(Prescriu prescriu) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosActualizados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, prescriu.getQuantitat());
            stmt.setString(2, prescriu.getDNI());
            stmt.setInt(3, prescriu.getNumCollegiat());
            stmt.setString(4, prescriu.getNomComercial());
            stmt.setDate(5, (Date) prescriu.getDates());

            registrosActualizados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosActualizados;
    }

    public int eliminar(Prescriu prescriu) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, prescriu.getDNI());
            stmt.setInt(2, prescriu.getNumCollegiat());
            stmt.setString(3, prescriu.getNomComercial());
            stmt.setDate(4, (Date) prescriu.getDates());

            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosEliminados;
    }
}