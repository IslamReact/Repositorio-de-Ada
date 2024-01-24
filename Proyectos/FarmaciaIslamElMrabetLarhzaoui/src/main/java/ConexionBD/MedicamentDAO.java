/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Medicament;
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
public class MedicamentDAO {
    private static final String SQL_SELECT = "SELECT NomComercial, Formula FROM Medicament";
    private static final String SQL_INSERT = "INSERT INTO Medicament (NomComercial, Formula) Values(?,?)";
    private static final String SQL_DELETE = "DELETE FROM Medicament WHERE NomComercial = ?";

    
    public static List<Medicament> seleccionarMedicaments() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Medicament> medicaments = new ArrayList<>();

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nomComercial = rs.getString("NomComercial");
                String formula = rs.getString("Formula");
                Medicament medicament = new Medicament(nomComercial, formula);
                medicaments.add(medicament);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.Conexion.close(rs);
            ConexionBD.Conexion.close(stmt);
            ConexionBD.Conexion.close(conn);
        }

        return medicaments;
    }

    public static int insertarMedicament(Medicament medicament) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosInsertados = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, medicament.getNomComercial());
            stmt.setString(2, medicament.getFormula());
            registrosInsertados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.Conexion.close(stmt);
            ConexionBD.Conexion.close(conn);
        }

        return registrosInsertados;
    }
    
    public static int eliminarMedicament(String nomComercial) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosEliminados = 0;

        try {
            conn = ConexionBD.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, nomComercial);
            registrosEliminados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            ConexionBD.Conexion.close(stmt);
            ConexionBD.Conexion.close(conn);
        }

        return registrosEliminados;
    }
}