/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author islam
 */
public class ConexionMySQL {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/Points_Of_Interest?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "";
    private static final String JDBC_PASSWORD = "" ;
    
    //Method that gets the connection
    public static Connection getConnection() throws SQLException{
      return DriverManager.getConnection  (JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    
    //This three methods close the different objects
    public static void close(ResultSet rs) throws SQLException{
        rs.close();
    }
    public static void close(Statement smtm) throws SQLException{
        smtm.close();
    }
    public static void close(Connection conn) throws SQLException{
        conn.close();
    }
}
