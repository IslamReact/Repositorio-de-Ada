/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Poi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author islam
 */
public class PoiDAO {

    private static final String SQL_SELECT_ORDER_BY = "SELECT poid, latitude, longitude, country, city, description, updated FROM pois_IE28 ORDER BY poid";
    private static final String SQL_SELECT_SINGLE_POID = "SELECT poid, latitude, longitude, country, city, description, updated FROM pois_IE28 WHERE poid = ?";
    private static final String SQL_SELECT_MANY_POIDS = "SELECT poid, latitude, longitude, country, city, description, updated FROM pois_IE28";
    private static final String SQL_INSERT = "INSERT INTO pois_IE28 (poid, latitude, longitude, country, city, description, updated) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM pois_IE28 WHERE poid = ?";
    private static final String SQL_DELETEALL = "DELETE FROM pois_IE28";
    private static final String SQL_COUNT = "SELECT COUNT(*) AS count FROM pois_IE28";
    
    public static List<Poi> selectPOIs() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Poi> pois = new ArrayList<>();

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ORDER_BY);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int poid = rs.getInt("poid");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String description = rs.getString("description");
                Date updated = rs.getDate("updated");

                Poi poi = new Poi(poid, latitude, longitude, country, city, description, updated);
                pois.add(poi);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.ConexionMySQL.close(rs);
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return pois;
    }
   
    public static Poi selectSinglePOI(int poid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Poi poi = null;

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_SINGLE_POID);
            stmt.setInt(1, poid); // Establecer el poid como parámetro en la consulta
            rs = stmt.executeQuery();

            if (rs.next()) {
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String description = rs.getString("description");
                Date updated = rs.getDate("updated");

                poi = new Poi(poid, latitude, longitude, country, city, description, updated);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.ConexionMySQL.close(rs);
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return poi;
    }


    public static int insertPOI(Poi poi) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosInsertados = 0;

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, poi.getPoid());
            stmt.setDouble(2, poi.getLatitude());
            stmt.setDouble(3, poi.getLongitude());
            stmt.setString(4, poi.getCountry());
            stmt.setString(5, poi.getCity());
            stmt.setString(6, poi.getDescription());
            stmt.setDate(7, poi.getUpdated());
            registrosInsertados = stmt.executeUpdate();
        } catch (SQLException ex) {
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("Error: La clave primaria ya existe en la base de datos.");
            } else {
                // Otros errores SQL
                ex.printStackTrace(System.out);
            }
        } finally {
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return registrosInsertados;
    }


    public static int deletePOI(int poid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, poid);
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return registrosEliminados;
    }
    
    public static int deleteAllPOIs() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_DELETEALL);
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return registrosEliminados;
    }
    
    public static int contarRegistros() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = ConexionBD.ConexionMySQL.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_COUNT);

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.ConexionMySQL.close(rs);
            ConexionBD.ConexionMySQL.close(stmt);
            ConexionBD.ConexionMySQL.close(conn);
        }

        return count;
    }
}
