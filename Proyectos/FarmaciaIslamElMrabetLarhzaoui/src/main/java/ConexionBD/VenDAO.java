/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Ven;
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
public class VenDAO {
    private static final String SQL_INSERT = "INSERT INTO ven (NomComercial, Cif, Dates, Quantitat) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT NomComercial, Cif, Dates, Quantitat FROM ven";
    private static final String SQL_DELETE_VENUTS = "DELETE FROM ven WHERE Cif = ?";
    
   public List<Ven> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Ven ven = null;
        List<Ven> ventas = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nomComercial = rs.getString("NomComercial");
                String cif = rs.getString("Cif");
                java.sql.Date dates = rs.getDate("Dates");
                String quantitat = rs.getString("Quantitat");

                ven = new Ven(nomComercial, cif, dates, quantitat);
                ventas.add(ven);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return ventas;
    }
    
    public static int insertar(Ven ven) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosInsertados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, ven.getNomComercial());
            stmt.setString(2, ven.getCif());
            stmt.setDate(3, (Date) ven.getDates());
            stmt.setString(4, ven.getQuantitat());

            registrosInsertados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosInsertados;
    }
    
    public static int eliminarMedicamentsVenduts(String cifFarmacia) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    int registrosEliminados = 0;

    try {
        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_DELETE_VENUTS);
        stmt.setString(1, cifFarmacia);
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

