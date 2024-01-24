/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Farmacia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaciaDAO {
    private static final String SQL_SELECT = "SELECT Cif, AdresaID FROM farmacia";
    private static final String SQL_SELECT_ADRESA = "SELECT AdresaID FROM Farmacia WHERE cif = ?";
    private static final String SQL_INSERT = "INSERT INTO farmacia (Cif, AdresaID) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE farmacia SET AdresaID = ? WHERE Cif = ?";
    private static final String SQL_DELETE = "DELETE FROM farmacia WHERE Cif = ?";

    public static List<Farmacia> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Farmacia> farmacias = new ArrayList<>();

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String cif = rs.getString("Cif");
                int adresaID = rs.getInt("AdresaID");

                Farmacia farmacia = new Farmacia(cif, adresaID);
                farmacias.add(farmacia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return farmacias;
    }

    public static int insertar(Farmacia farmacia) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;
        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, farmacia.getCif());
            stmt.setInt(2, farmacia.getAdresaID()); // Obtener el AdresaID desde la referencia a Adresa
            registro = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(stmt);
            } catch (SQLException ex) {
            }
            try {
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public int actualizar(Farmacia farmacia) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;
        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, farmacia.getAdresaID()); // Obtener el AdresaID desde la referencia a Adresa
            stmt.setString(2, farmacia.getCif());
            registro = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(stmt);
            } catch (SQLException ex) {
            }
            try {
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public int eliminar(String cif) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, cif);
            registrosEliminados = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                Conexion.close(stmt);
            } catch (SQLException ex) {
            }
            try {
                Conexion.close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

        return registrosEliminados;
    }
    
    // Función para obtener el adresaID de una Farmacia por su CIF
    public static int obtenerAdresaID(String cif) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int adresaID = -1; // Valor predeterminado si no se encuentra

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ADRESA);
            stmt.setString(1, cif);
            rs = stmt.executeQuery();

            if (rs.next()) {
                adresaID = rs.getInt("adresaID");
            }
        } finally {
            // Cierra recursos (ResultSet, Statement, Connection)
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return adresaID;
    }
}

