/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import static ConexionBD.Conexion.close;
import static ConexionBD.Conexion.getConnection;
import Domain.Pacient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author islam
 */
public class PacientDAO {
    private static final String SQL_SELECT = "SELECT DNI, Nom, Cognoms FROM Pacient";
    private static final String SQL_INSERT = "INSERT INTO Pacient (DNI, Nom, Cognoms) Values(?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Pacient SET Nom = ?, Cognoms = ? WHERE DNI = ?";
    private static final String SQL_DELETE = "DELETE FROM Pacient WHERE DNI = ?";
    
    public static List<Pacient> seleccionar() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pacient pacient = null;
        List<Pacient> pacients = new ArrayList<>();
        
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                String dNI = rs.getString("DNI");
                String nom = rs.getString("Nom");
                String cognom = rs.getString("Cognoms");
                pacient = new Pacient(dNI,nom,cognom);
                pacients.add(pacient);
            }
            
         
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
     return pacients;
    }
    
    public static int insertar(Pacient pacient) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, pacient.getdNI());
            stmt.setString(2, pacient.getNom());
            stmt.setString(3, pacient.getCognoms());
            registro = stmt.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace(System.out);
        }
        finally{
            try{
                close(stmt);
            }catch (SQLException ex){   
            }
            try {
                close(conn);
            }catch(SQLException ex){
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public static int actualizar(Pacient pacient) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, pacient.getNom());
            stmt.setString(2, pacient.getCognoms());
            stmt.setString(3, pacient.getdNI());

            registro = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
            } catch (SQLException ex) {
            }
            try {
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }
    
    public static int eliminar(String dNI) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, dNI);
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
            } catch (SQLException ex) {
            }
            try {
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registrosEliminados;
    }
    
}


