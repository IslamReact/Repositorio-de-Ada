/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import Domain.Poi;

import static Domain.Poi.convertDocumentToPOI;

import static Management.Tools.convertDateToString;
import static Management.Tools.getIntInput;
import static Management.Tools.readDouble;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
 
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mycompany.puntodeinteresada.PuntoDeInteresADA;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


/**
 *
 * @author islam
 */

public class UserMongoDAO {
    
    public static Scanner sc = new Scanner(System.in);
    // TIP: We only use ONE Scanner.
    // We can pass the instance to every method or create this static global
    // variable
    

    /*public UserMongoDAO() {
        conn = connectToMongoClient();
        
        //TIP: Connection for every single user to not cross connections
    }    
    */

    public static void insert(MongoClient conn,String databaseName, String collectionName) throws SQLException {
        /*
        * Static method: Get item list from keyboard
        * 
        */
        MongoDatabase database = conn.getDatabase(databaseName);

        System.out.println("===================================================");
        System.out.println("Enter POI details or type 0 for the poid to finish:");
        System.out.println("===================================================");
        
        while (true) {
            
            System.out.print("POID: ");
            int poid = getIntInput(sc);
            if (poid == 0) {//If poid equals 0 will stop inserting documents
                break;
            }else if(!PuntoDeInteresADA.poiExistsForMongoDB(conn, poid, databaseName, collectionName)){
                System.out.print("Latitude: ");
                double latitude = readDouble(sc);

                System.out.print("Longitude: ");
                double longitude = readDouble(sc);

                System.out.print("Country: ");
                String country = sc.nextLine();

                System.out.print("City: ");
                String city = sc.nextLine();

                System.out.print("Description: ");
                String description = sc.nextLine();

                 // We dont let choose date because is when it was updated
                String formattedDate = convertDateToString();


                // Document that structures the atributes to insert them
                Document document = new Document("poid", poid)
                        .append("latitude", latitude)
                        .append("longitude", longitude)
                        .append("country", country)
                        .append("city", city)
                        .append("description", description)
                        .append("updated", formattedDate);


                database.getCollection(collectionName).insertOne(document);
                System.out.println("POI inserted successfully!");
            }else{
                System.out.println("This poid already exist in database, enter another");
            }

        }
       
    }
    
    public static void insertSingleDocument(MongoClient conn, String databaseName, String collectionName, Poi poi) throws SQLException {
        /*
        * Static method: inserts a single poid, by the poi in the args
        * 
        */
        MongoDatabase database = conn.getDatabase(databaseName);

        int existentPoi = poi.getPoid();
        if(!PuntoDeInteresADA.poiExistsForMongoDB(conn,existentPoi,databaseName,collectionName)){     
            Document document = new Document("poid", poi.getPoid())
                    .append("latitude", poi.getLatitude())
                    .append("longitude", poi.getLongitude())
                    .append("country", poi.getCountry())
                    .append("city", poi.getCity())
                    .append("description", poi.getDescription());

            // we need to format date for mongo
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(poi.getUpdated());
            document.append("updated", formattedDate);

            database.getCollection(collectionName).insertOne(document);
            System.out.println("POI inserted successfully!");
        }else{
            System.out.println("Poid " + existentPoi + " ya existe en la base de datos");
        }
    }
    
    //NOT IMPLEMENTED, STATIC METHOT THAT ONLY PRINTS.
    /*public static void listPois(MongoClient conn, String databaseName, String collectionName) {
    Static method: list all items from collection
        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> col = database.getCollection(collectionName);

        Bson projection = Projections.fields(
                Projections.include("poid", "latitude", "longitude", "country", "city", "description", "updated"),
                Projections.excludeId());
        MongoCursor<Document> cursor = col.find().projection(projection).iterator();

        // Format like csv
        System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
        System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
        System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");

        int count = 0;
        while (cursor.hasNext()) {
            Document item_doc = cursor.next();
            System.out.printf("| %-5d | %-9.6f | %-10.6f | %-20s | %-20s | %-20s | %-20s |\n",
                    item_doc.getInteger("poid"),
                    item_doc.getDouble("latitude"),
                    item_doc.getDouble("longitude"),
                    item_doc.getString("country"),
                    item_doc.getString("city"),
                    item_doc.getString("description"),
                    item_doc.getString("updated"));

            count++;
            if (count % 4 == 0) {
                Tools.pressAnyKeyToContinue(); // Ask to press any key to continue
            }
        }

        System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
    }*/
    
    public static List<Poi> listPoisArray(MongoClient conn, String databaseName, String collectionName) {
        /*
        * Static method: Lists all pois and sets them into an array
        * 
        * Returns resultList
        */
        List<Poi> resultList = new ArrayList<>();

        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> col = database.getCollection(collectionName);

        Bson projection = Projections.fields(
                Projections.include("poid",
                        "latitude",
                        "longitude",
                        "country", 
                        "city",
                        "description",
                        "updated"),
                Projections.excludeId());
        MongoCursor<Document> cursor = col.find().projection(projection).iterator();

        while (cursor.hasNext()) {
            Document item_doc = cursor.next();
            Poi poi = convertDocumentToPOI(item_doc);
            resultList.add(poi);
        }

        return resultList;
    }
    
    public static Poi listsSinglePoi(MongoClient conn, String databaseName, String collectionName,int poid ) {
        /*
        * Static method: Lists all pois and sets them into an array
        * 
        * Returns resultList
        */
        Poi selectedPoi = new Poi();
        
        Bson comparison = eq("poid", poid); // equal

        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> col = database.getCollection(collectionName);

        Bson projection = Projections.fields(
                Projections.include("poid",
                        "latitude",
                        "longitude",
                        "country", 
                        "city",
                        "description",
                        "updated"),
                Projections.excludeId());
        MongoCursor<Document> cursor = col.find(comparison).projection(projection).iterator();

        while (cursor.hasNext()) {
            Document item_doc = cursor.next();
            selectedPoi = convertDocumentToPOI(item_doc);
        }

        return selectedPoi;
    }
    
    public static List<Poi> listMultiplePois(MongoClient conn, String databaseName, String collectionName, List<Integer> poidList) {
    /*
    * Static method: Lists multiple pois and sets them into a list
    * 
    * Returns resultList
    */
    List<Poi> resultList = new ArrayList<>();
    MongoDatabase database = conn.getDatabase(databaseName);
    MongoCollection<Document> col = database.getCollection(collectionName);

    Bson projection = Projections.fields(
            Projections.include("poid", 
                    "latitude", 
                    "longitude", 
                    "country", 
                    "city", 
                    "description", 
                    "updated"),
            Projections.excludeId());

    for (Integer poid : poidList) {
        Bson comparison = eq("poid", poid);
        MongoCursor<Document> cursor = col.find(comparison).projection(projection).iterator();

        while (cursor.hasNext()) {
            Document item_doc = cursor.next();
            resultList.add(convertDocumentToPOI(item_doc));
        }
    }

    return resultList;
    }
    
    public static int deleteSinglePoi(MongoClient conn, String databaseName, String collectionName, int poidToDelete) {
    /*
    * Static method: Deletes a single poi based on a specific POID
    * 
    * Returns the number of documents deleted (0 o 1)
    */
        int deletedCount = 0;
        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> col = database.getCollection(collectionName);

        Bson filter = eq("poid", poidToDelete);
        DeleteResult deleteResult = col.deleteOne(filter);
        deletedCount = (int) deleteResult.getDeletedCount();

        return deletedCount;
    }   
    
    public static List<Integer> deleteMultiplePois(MongoClient conn, String databaseName, String collectionName, List<Integer> poidList) {
    /*
    * Static method: Deletes multiple pois and returns a list with the deleted POIDs
    *
    * Returns the list of pois deleted
    */
    List<Integer> deletedPoids = new ArrayList<>();
    MongoDatabase database = conn.getDatabase(databaseName);
    MongoCollection<Document> col = database.getCollection(collectionName);

    for (Integer poid : poidList) {
        Bson filter = eq("poid", poid);
        DeleteResult deleteResult = col.deleteOne(filter);

        if (deleteResult.getDeletedCount() > 0) {
            deletedPoids.add(poid);
        }
    }

    return deletedPoids;
    }
    
    public static void deleteAllDocuments(MongoClient conn, String databaseName, String collectionName) {
        /*
        * Static method: delete all documents from collection
        */
        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        collection.deleteMany(new Document());
        System.out.println("All documents have been deleted from the collection: " + collectionName);
    }
    
    public static long countDocuments(MongoClient conn, String databaseName, String collectionName){
        /*
        * Static method: Counts all items from collection and returns a long number
        * 
        * Returns documentsCount
        */
        long documentsCount = 0;
        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection collection = database.getCollection(collectionName);
        
        return documentsCount = collection.countDocuments();
    }
    
   public static void upsertSinglePoi(MongoClient conn, String databaseName, String collectionName, Poi poi) {
    /*
    * Static method: Performs an upsert (update or insert) for a single POI
    */
        MongoDatabase database = conn.getDatabase(databaseName);
        MongoCollection<Document> col = database.getCollection(collectionName);

        Document query = new Document("poid", poi.getPoid());
        Document update = new Document("$set",
                new Document("latitude", poi.getLatitude())
                        .append("longitude", poi.getLongitude())
                        .append("country", poi.getCountry())
                        .append("city", poi.getCity())
                        .append("description", poi.getDescription())
                        .append("updated", new SimpleDateFormat("yyyy-MM-dd").format(poi.getUpdated())));

        UpdateOptions options = new UpdateOptions().upsert(true);

        col.updateOne(query, update, options);

        System.out.println("===========================");
        System.out.println("POI upserted successfully!!");
        System.out.println("===========================");
    }

}