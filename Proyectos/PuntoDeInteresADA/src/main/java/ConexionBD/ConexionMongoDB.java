/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import static Management.MangementMongoDB.createCollectionIfNotExists;
import static Management.Tools.disableMongoLogging;
import static Management.Tools.waitTimeUser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author islam
 */
public class ConexionMongoDB {
    public static MongoClient connectToMongoClient(){
    /*
    * Static method that connects to client
    */  
        try{
            disableMongoLogging();
            MongoClient dbClient = new MongoClient();
            return dbClient;
        }catch(Exception ex){
            System.out.println("Something wrong connecting!");
            ex.printStackTrace(System.out);
        }
        return null;
    }
    
    public static void closeMongoClient(MongoClient c){
    /*
    * Static method that closes connnection with client
    */
        try{
            c.close();
        }catch(Exception ex){
            System.out.println("Unable to close!");
            ex.printStackTrace(System.out);
        }
    }
    
    public static MongoDatabase useDBMongo(MongoClient conn,String database,String collectionSelected) {
        // Getting a connection 
        System.out.println("Connecting with MongoDB");
        waitTimeUser();
        conn =  connectToMongoClient();
        MongoDatabase db = null;

        try{
            db = conn.getDatabase(database);
            createCollectionIfNotExists(db,collectionSelected);
        }catch(Exception ex){
            System.out.println("Something wrong accesing!");
            ex.printStackTrace(System.out);
        }
        return db;
    }
    
    public static MongoDatabase selectDBMongo(MongoClient conn,String database) {
        // Getting a connection 
        conn =  connectToMongoClient();
        MongoDatabase db = null;

        try{
            db = conn.getDatabase(database);
        }catch(Exception ex){
            System.out.println("Something wrong accesing!");
            ex.printStackTrace(System.out);
        }
        return db;
    }
}
