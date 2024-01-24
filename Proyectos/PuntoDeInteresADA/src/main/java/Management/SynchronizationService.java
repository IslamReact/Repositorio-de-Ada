package Management;

import static ConexionBD.PoiDAO.deleteAllPOIs;
import static ConexionBD.PoiDAO.insertPOI;
import static ConexionBD.UserMongoDAO.deleteAllDocuments;
import static ConexionBD.UserMongoDAO.insert;
import static ConexionBD.UserMongoDAO.insertSingleDocument;
import Domain.Poi;
import static Management.Tools.waitTimeUser;
import com.mongodb.MongoClient;
import java.sql.SQLException;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author islam
 */
public class SynchronizationService {

    public static void synchronizeMongoDB(MongoClient conn,String databaseName, String collectionName, List<Poi> listapois) throws SQLException {
        /*
        * Static method: synchronize a an array of objects to a MongoDB database
        */
        System.out.println("Synchronizing MongoDB database...");
        waitTimeUser();
        deleteAllDocuments(conn,databaseName,collectionName);
        for(Poi poisToSyinchronize : listapois){
            insertSingleDocument(conn,databaseName,collectionName,poisToSyinchronize);
        }
        
        System.out.println("MongoDB synchronization completed.");
    }

    public static void synchronizeMySQL(List<Poi> listapois) throws SQLException {
        /*
        * Static method: synchronize a an array of objects to a MySQL database
        */
        System.out.println("Synchronizing MySQL database...");
        waitTimeUser();
        deleteAllPOIs();
        for(Poi poisToSyinchronize : listapois){
            insertPOI(poisToSyinchronize);
        }
        // Agrega tu lógica de sincronización aquí
        System.out.println("MySQL synchronization completed.");
    }
}