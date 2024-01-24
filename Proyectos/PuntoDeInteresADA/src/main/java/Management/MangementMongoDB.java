/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Management;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.util.List;

/**
 *
 * @author islam
 */
public class MangementMongoDB { 
    public static boolean collectionExists(String collectionName, MongoDatabase database){
    /*
    * Static method to check if a collection exists
    */  
        MongoIterable<String> collection = database.listCollectionNames();
        for(String s : collection){
            if(s.equals(collectionName)){
                return true;
            }
        }
        return false;
    }    
    
    public static Boolean createCollectionIfNotExists(MongoDatabase database,String collection){
    /*
    * Static method to create a collection, calling function collectionExists() to check if not exists first
    */    
        try{
            if(collectionExists(collection,database)){
                System.out.println("This collection exists.");
                return true;
            }else{
                database.createCollection(collection);
                System.out.println("Created collection" + collection + "...");
            }
        }catch(Exception ex){
            System.err.println("Something wrong creating Collection!");
            ex.printStackTrace(System.out);
        }
        return false;
    }
    
    public static void printDatabases(MongoClient mongo) {
        List dbs = mongo.getDatabaseNames();
        for (Object db : dbs) {
            System.out.println(" - " + db);
        }
    }
    
    public static void printCollections(MongoDatabase db){
    /*
    * Static method to print collections
    */
        System.out.println("Colecctions:\n" +
        "-----------------------------------------");
        MongoIterable<String> collection = db.listCollectionNames();
        for(String s : collection){
            System.out.println(" - " + s);
        }
    }
}
