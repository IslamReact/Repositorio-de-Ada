/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Adresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresaDAO {
    private static final String SQL_SELECT = "SELECT AdresaID, Codi_Postal, Provincia, Carrer, NumCarrer, Cif FROM adresa";
    private static final String SQL_INSERT = "INSERT INTO adresa (Codi_Postal, Provincia, Carrer, NumCarrer, Cif) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM adresa WHERE AdresaID = ?";

    public List<Adresa> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Adresa adresa = null;
        List<Adresa> adresses = new ArrayList<>();

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int adresaID = rs.getInt("AdresaID");
                int codi_Postal = rs.getInt("Codi_Postal");
                String Provincia = rs.getString("Provincia");
                String carrer = rs.getString("Carrer");
                int numCarrer = rs.getInt("NumCarrer");
                String cif = rs.getString("Cif");
                adresa = new Adresa(adresaID, codi_Postal, Provincia, carrer, numCarrer, cif);
                adresses.add(adresa);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return adresses;
    }

    public static int insertar(Adresa adresa) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int adresaID = 0;

        try {
            conn = Conexion.getConnection();

            // Inserta la Adresa sin la referencia a la Farmacia (CIF)
            String sqlInsertAdresa = "INSERT INTO adresa (Codi_Postal, Provincia, Carrer, NumCarrer, Cif) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlInsertAdresa, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, adresa.getCodi_Postal());
            stmt.setString(2, adresa.getProvincia());
            stmt.setString(3, adresa.getCarrer());
            stmt.setInt(4, adresa.getNumCarrer());
            stmt.setString(5, adresa.getCif());
             
            int registrosInsertados = stmt.executeUpdate();
            if (registrosInsertados > 0) {
                // Si la inserción fue exitosa, obtén el ID de la Adresa
                rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    adresaID = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return adresaID;
    }

    public static int eliminarAdresa(int adresaID) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, adresaID);
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosEliminados;
    }
    
    public static Adresa obtenerAdresaPorID(int adresaID) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Adresa adresa = null;

        try {
            conn = Conexion.getConnection();
            String SQL_SELECT_BY_ID = "SELECT Codi_Postal, Provincia, Carrer, NumCarrer, Cif FROM adresa WHERE AdresaID = ?";
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, adresaID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int codigoPostal = rs.getInt("Codi_Postal");
                String provincia = rs.getString("Provincia");
                String calle = rs.getString("Carrer");
                int numeroCalle = rs.getInt("NumCarrer");
                String cif = rs.getString("Cif");

                adresa = new Adresa(adresaID, codigoPostal, provincia, calle, numeroCalle, cif);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return adresa;
    }
    
    // Método para obtener el ID de la farmacia por su CIF
private static int obtenerIdFarmaciaPorCif(String cif) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int idFarmacia = 0;

    try {
        conn = Conexion.getConnection();
        String sqlSelectFarmacia = "SELECT AdresaID FROM farmacia WHERE Cif = ?";
        stmt = conn.prepareStatement(sqlSelectFarmacia);
        stmt.setString(1, cif);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idFarmacia = rs.getInt("AdresaID");
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    } finally {
        Conexion.close(rs);
        Conexion.close(stmt);
        Conexion.close(conn);
    }

    return idFarmacia;
}
}