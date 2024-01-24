/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import static ConexionBD.Conexion.close;
import static ConexionBD.Conexion.getConnection;
import Domain.Metge;
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
public class MetgeDAO {
    private static final String SQL_SELECT = "SELECT NumCollegiat, Especialitat, Nom, Cognoms FROM Metge";
    private static final String SQL_INSERT = "INSERT INTO Metge (NumCollegiat, Especialitat, Nom, Cognoms) Values(?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Metge SET Especialitat = ?, Nom = ?, Cognoms = ? WHERE NumCollegiat = ?";
    private static final String SQL_DELETE = "DELETE FROM Metge WHERE NumCollegiat = ?";
    
    public static List<Metge> seleccionar() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Metge persona = null;
        List<Metge> metges = new ArrayList<>();
        
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                int numCollegiat = rs.getInt("NumCollegiat");
                String especialitat = rs.getString("Especialitat");
                String nom = rs.getString("Nom");
                String cognom = rs.getString("Cognoms");
                persona = new Metge(numCollegiat,especialitat,nom,cognom);
                metges.add(persona);
            }
            
         
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
     return metges;
    }
    
    public int insertar(Metge metge) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, metge.getNumCollegiat());
            stmt.setString(2, metge.getEspecialitat());
            stmt.setString(3, metge.getNom());
            stmt.setString(4, metge.getCognoms());
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

    
    public static int actualizar(Metge metge) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosActualizados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, metge.getEspecialitat());
            stmt.setString(2, metge.getNom());
            stmt.setString(3, metge.getCognoms());
            stmt.setInt(4, metge.getNumCollegiat());

            registrosActualizados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosActualizados;
    }

    public static int eliminar(int numCollegiat) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, numCollegiat);

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
