/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.puntodeinteresada;

// Imports related to MongoDB connections and database handling
import static ConexionBD.ConexionMongoDB.closeMongoClient; // Closing the MongoDB client
import static ConexionBD.ConexionMongoDB.useDBMongo; // Using a MongoDB database
import static ConexionBD.ConexionMongoDB.connectToMongoClient; // Connecting to the MongoDB client
import static ConexionBD.ConexionMongoDB.selectDBMongo; // Selecting a MongoDB database
import ConexionBD.PoiDAO; // Data Access Object (DAO) for Poi objects in MongoDB
import ConexionBD.UserMongoDAO;

// Imports related to MongoDB operations management
import static ConexionBD.UserMongoDAO.countDocuments; // Counting documents in MongoDB
import static ConexionBD.UserMongoDAO.insert; // Inserting data into MongoDB
import static ConexionBD.UserMongoDAO.insertSingleDocument;
import static ConexionBD.UserMongoDAO.listMultiplePois;
import static ConexionBD.UserMongoDAO.listsSinglePoi;
import static ConexionBD.UserMongoDAO.upsertSinglePoi;

import Domain.Poi; // Domain class for Poi objects

import static Management.FilesManagement.ExportXML.guardarXMLpois;
import Management.FilesManagement.ImportXML;

// Imports related to tools and utilities management
import static Management.MangementMongoDB.printDatabases; // Printing databases in MongoDB
import static Management.MangementMongoDB.printCollections; // Printing collections in MongoDB
import static Management.SynchronizationService.synchronizeMongoDB; // Synchronizing MongoDB databases
import static Management.SynchronizationService.synchronizeMySQL; // Synchronizing MySQL databases

import Management.Tools; // General tools and utilities
import static Management.Tools.getIntInput;
import static Management.Tools.readDouble; // Reading double values
import static Management.Tools.waitTimeUser; // User wait time

import com.mongodb.MongoClient; // MongoDB client
import com.mongodb.client.MongoDatabase; // MongoDB database

import java.io.IOException;
import java.sql.Date; // SQL Date class
import java.sql.SQLException; // SQL Exception
import java.util.ArrayList;
import java.util.List; // Generic List
import java.util.Random; // Random number generation
import java.util.Scanner; // Scanner class for user input

/**
 *
 * @author islam
 */
public class PuntoDeInteresADA {

    private static MongoClient conn = connectToMongoClient();
    private static final Scanner sc = new Scanner(System.in);//TIP: MAKE ONE SCANNER TO NOT COLLAPSE BUFFER
    private static final String[] welcomeMessages = {
        " ----------------------------------------------------------\n"
        + "|  Welcome to the database manager of Points Of Interest!  |\n"
        + " ----------------------------------------------------------",
        " -----------------------------------------------------------------\n"
        + "|  Greetings! You are in the Points Of Interest database manager. |\n"
        + " -----------------------------------------------------------------",
        " ------------------------------------------------------------------------------\n"
        + "|  Hello! Here you have control over your points of interest in the database.  |\n"
        + " ------------------------------------------------------------------------------"
    };

    private static final String welcomeMessageExplanation
            = "This application serves as a comprehensive manager for your Points of Interest. "
            + "Here's how it works:\n"
            + "- You can choose between MongoDB and MySQL as your database systems.\n"
            + "- Synchronization is available between the selected databases.\n"
            + "- For MongoDB, you can select databases and collections, and then:\n"
            + "     * Insert new items manually\n"
            + "     * List existing items\n"
            + "     * Delete items\n"
            + "     * Synchronize the database\n"
            + "     * Import items\n"
            + "- For MySQL, options include:\n"
            + "     * Inserting new items manually\n"
            + "     * Listing existing items\n"
            + "     * Deleting items\n"
            + "     * Synchronizing the database\n"
            + "     * Importing items\n"
            + "- The program will guide you through the functionalities based on your selections.\n"
            + "- Press any key to continue at certain points to display more results or actions.\n"
            + "- You can exit the application by typing 'exit'.\n"
            + "Enjoy managing your Points of Interest!";

    private static String getRandomWelcomeMessage() {
        Random random = new Random();
        int index = random.nextInt(welcomeMessages.length);
        return welcomeMessages[index];
    }

    //Function to validate if a point of interest exists in database
    private static boolean poiExists(int poid) throws SQLException {
        List<Poi> poisExists = PoiDAO.selectPOIs();
        for (Poi pois : poisExists) {
            if (pois.getPoid() == poid) {
                return true;
            }
        }
        return false;
    }

    public static boolean poiExistsForMongoDB(MongoClient conn, int poid, String databaseName, String collectionName) throws SQLException {
        List<Poi> poisExists = UserMongoDAO.listPoisArray(conn, databaseName, collectionName);
        for (Poi pois : poisExists) {
            if (pois.getPoid() == poid) {
                return true;
            }
        }
        return false;
    }

    private static Poi createPOI(Scanner sc, Integer poid) {

        System.out.print("Latitude: ");
        Double latitude = readDouble(sc);

        System.out.print("Longitude: ");
        Double longitude = readDouble(sc);

        System.out.print("Country: ");
        String country = sc.nextLine();

        System.out.print("City: ");
        String city = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        // We dont let choose date because is when it was updated
        Date updated = new Date(System.currentTimeMillis());

        return new Poi(poid, latitude, longitude, country, city, description, updated);
    }

    public static ArrayList<Integer> askAndStorePoidsInArray() {
        int poid = 0;
        ArrayList poids = new ArrayList<Integer>();
        System.out.println("Enter one by one all poids you want(Type 0 to finish)");
        do {
            poid = getIntInput(sc);
            if (poid != 0) {
                poids.add(poid);
            }
        } while (poid != 0);
        return poids;
    }

    private static void runMySQLMenu(String database, String collection) throws SQLException, IOException {
        String option;
        long registrosMySQL;
        long registrosMongodb;
        
        do {
            registrosMySQL = PoiDAO.contarRegistros();
            registrosMongodb = countDocuments(conn, database, collection);
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║                  MySQL Menu                  ║");
            System.out.println("  ║ Entries: " + registrosMySQL + " in the database                  ║");
            System.out.println("╚══════════════════════════════════════════════╝\n");

            System.out.println("Choose an option:");
            System.out.println("────────────────────────────────────────────────");
            System.out.println("║ 1. Insert new items manually (Type 0 in the POID to exit)");
            System.out.println("║ 2. List items");
            System.out.println("║ 3. Delete items");
            if (registrosMySQL > registrosMongodb) {
                System.out.println("║ 4. Synchronize database");
            }
            System.out.println("║ 5. Import items (Only XML)");
            System.out.println("║ 6. Export items (Only XML)");
            System.out.println("║ 7. Back to database selection...\n");
     
            option = sc.nextLine();

            switch (option) {
                case "1":
                    Integer poid = null;
                    try {
                        System.out.println("Write the point of interest data (enter 0 for POID to finish)");
                        do {
                            System.out.print("POID: ");
                            poid = getIntInput(sc);
                            if (poiExists(poid)) {
                                System.out.println("This poid already exists. Type another one");
                            } else {
                                if (poid != 0) {
                                    Poi poi = createPOI(sc, poid);
                                    int registrosInsertados = PoiDAO.insertPOI(poi);
                                    if (registrosInsertados > 0) {
                                        System.out.println(" -------------------------------------\n"
                                                + "| Point of interest succesfully added |\n"
                                                + " -------------------------------------\n");
                                    } else {
                                        System.out.println("Error while inserting Point of interest");
                                    }
                                }
                            }
                        } while (poid != 0);

                    } catch (SQLException ex) {
                        ex.printStackTrace(System.out);
                    }
                    break;
                case "2":// Show pois
                    int opcion2;
                    System.out.println("=======================================");
                    System.out.println("|              List Menu              |");
                    System.out.println("=======================================");
                    System.out.println("|     1. List all items               |");
                    System.out.println("|     2. List item by POID            |");
                    System.out.println("|     3. List items by different POIDS|");
                    System.out.println("=======================================");
                    opcion2 = getIntInput(sc);

                    switch (opcion2) {//Call to function selectPOIs() that returns th list of pois ordered by id
                        case 1: {
                            List<Poi> allpois = PoiDAO.selectPOIs();
                            int count = 0;
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");

                            for (Poi poi : allpois) {
                                System.out.println(poi);
                                count++;

                                if (count % 4 == 0) {
                                    Tools.pressAnyKeyToContinue(); //Ask to press any key to continue
                                }
                            }
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 2: {//Call to function selectSinglePOI() to search a poi by the id
                            System.out.println("Please insert POID number");
                            int poidSearch = getIntInput(sc);
                            if (poiExists(poidSearch)) {
                                Poi poibyPoid = PoiDAO.selectSinglePOI(poidSearch);
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                                System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                                System.out.println(poibyPoid);
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                                Tools.pressAnyKeyToContinue();
                            } else {
                                System.out.println("This poid doesn´t exist");
                            }
                            Tools.pressAnyKeyToContinue();
                            break;

                        }
                        case 3: {//Call to function selectSinglePOI() multiple times to insert them in an array, then show the selected pois
                            ArrayList poidsToShow;
                            List<Poi> individuallyselectedPois = new ArrayList<>();
                            poidsToShow = askAndStorePoidsInArray();
                            int count = 0;

                            for (int i = 0; i < poidsToShow.size(); i++) {
                                int individualPoid = (int) poidsToShow.get(i);
                                if (poiExists(individualPoid)) {
                                    individuallyselectedPois.add(PoiDAO.selectSinglePOI(individualPoid));
                                } else {
                                    System.out.println("===============================================");
                                    System.out.println("Error. Poid " + individualPoid + " doesn't exist");
                                }
                            }

                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");

                            for (Poi poi : individuallyselectedPois) {
                                System.out.println(poi);
                                count++;

                                if (count % 4 == 0) {
                                    Tools.pressAnyKeyToContinue(); //Ask to press any key to continue
                                }
                            }
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        default:
                            System.out.println("Select an option");
                    }
                    break;
                case "3":// Delete pois
                    int opcion3;
                    System.out.println("=======================================");
                    System.out.println("|            Delete Menu               |");
                    System.out.println("=======================================");
                    System.out.println("|    1. Delete all items              |");
                    System.out.println("|    2. Delete item by POID           |");
                    System.out.println("|    3. Delete items by different POIDS|");
                    System.out.println("=======================================");

                    opcion3 = getIntInput(sc);

                    switch (opcion3) {
                        case 1: {
                            String choice;
                            do {
                                System.out.println("Are you sure that you want to delete all data? (Yes/No)");
                                choice = sc.nextLine().toLowerCase();
                                switch (choice) {
                                    case "yes":
                                        int deletedRegitsers = PoiDAO.deleteAllPOIs();
                                        if (deletedRegitsers > 0) {
                                            System.out.println(" ---------------------------------------\n"
                                                    + "| Point of interest succesfully deleted |\n"
                                                    + " ---------------------------------------\n");
                                        } else {
                                            System.out.println("Error while deleting all Points Of Interest");
                                        }
                                        break;
                                    case "no":
                                        System.out.println("Going back...");
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please enter Yes or No.");
                                        break;
                                }
                            } while (!choice.equals("yes") && !choice.equals("no"));
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 2: {
                            System.out.println("Type the poid to delete the Point of Interest");
                            poid = getIntInput(sc);
                            if (poiExists(poid)) {
                                int deletedRegitsers = PoiDAO.deletePOI(poid);

                                if (deletedRegitsers > 0) {
                                    System.out.println("Point of interest deleted succesfully");
                                } else {
                                    System.out.println("Error while deleting the point Of interest");
                                }
                            } else {
                                System.out.println("This poid doesn´t exist");
                            }
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 3: {
                            int deletedRegitsers = 0;
                            ArrayList poidsToDelete;
                            poidsToDelete = askAndStorePoidsInArray();
                            for (int i = 0; i < poidsToDelete.size(); i++) {
                                int individualPoid = (int) poidsToDelete.get(i);
                                if (poiExists(individualPoid)) {
                                    PoiDAO.deletePOI(individualPoid);
                                    deletedRegitsers++;
                                    System.out.println("===============================================");
                                    System.out.println("Poid " + individualPoid + " succesfully deleted");
                                } else {
                                    System.out.println("===============================================");
                                    System.out.println("Error. Poid " + individualPoid + " doesn't exist");
                                }
                            }
                            if (deletedRegitsers == poidsToDelete.size()) {
                                System.out.println();
                                System.out.println("All points of interest deleted succesfully");
                            } else {
                                System.out.println();
                                System.out.println("Oooopsidopsi! There was an error while trying to delete some poids");
                            }
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        default:
                            System.out.println("Select an option");
                    }
                    break;
                case "4":
                    if(registrosMySQL > registrosMongodb){
                    String choice;
                        System.out.println("Are you sure that you want to syinchronize this database with MongoDB?(This will delete all from database)");
                        do {
                            choice = sc.nextLine().toLowerCase();
                        switch (choice) {
                            case "yes":
                                List<Poi> listapois = PoiDAO.selectPOIs();
                                synchronizeMongoDB(conn, database, collection,listapois);
                                break;
                            case "no":
                                System.out.println("Going back...");
                                break;
                            default:
                                System.out.println("Invalid input. Please enter Yes or No.");
                                break;
                        }
                        } while (!choice.equals("yes") && !choice.equals("no"));
                        Tools.pressAnyKeyToContinue();
                    }else{
                        System.out.println("Invalid option. Please choose a valid option.");
                    }
                    break;
                case "5":
                    String ruta;
                    System.out.println("Por favor especifica la ruta del archivo que desea importar");
                    ruta = sc.nextLine();
                    
                    List<Poi> newPoisToImport;  
                    newPoisToImport = ImportXML.cargarListaPOI(ruta);
                    System.out.println("=======================================");
                        System.out.println("This data will be imported:");
                        System.out.println("=======================================");
                    
                    for (Poi pois : newPoisToImport) {
                        System.out.println(pois);
                    }
                    System.out.println();
                    
                    if (!newPoisToImport.isEmpty()) {
                        System.out.println("Type 'add' to add this data or 'delete' if you want to delete all data from the database and then add this one");
                        String choice2;
                        do {
                            choice2 = sc.nextLine().toLowerCase();
                            switch (choice2) {
                                case "add":
                                    for (Poi pois : newPoisToImport) {
                                        PoiDAO.insertPOI(pois);
                                    }   break;
                                case "delete":
                                    System.out.println("We will proceed to delete all data from the database and import the new one...");
                                    PoiDAO.deleteAllPOIs();
                                    for (Poi pois : newPoisToImport) {
                                        PoiDAO.insertPOI(pois);
                                    }   break;
                                default:
                                    System.out.println("Invalid input. Please enter 'add' or 'delete'.");
                                    break;
                            }
                        } while (!choice2.equals("add") && !choice2.equals("delete"));
                    } else {
                        System.out.println("=======================================");
                        System.out.println("The file you want to import is empty!!!");
                        System.out.println("=======================================");
                    }    
                    Tools.pressAnyKeyToContinue();
                    break;
                case "6":
                    if(guardarXMLpois(PoiDAO.selectPOIs())){
                        System.out.println("File exported succesfully! Look in your desktop.");
                    }
                    break;
                case "7":
                    System.out.println("Returning to the database selection.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (!option.equals("7"));

    }

    private static void runMongoDBMenu(MongoClient conn, String database, String collection) throws SQLException, IOException {
        String option;
        long registrosMySQL;
        long documentsCount;
        
        do {
            documentsCount = countDocuments(conn, database, collection);
            registrosMySQL = PoiDAO.contarRegistros();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                    MongoDB Menu                        ║");
            System.out.println(  "║      Entries: " + documentsCount + " in the database                        ║");
            System.out.println("╚════════════════════════════════════════════════════════╝\n");

            System.out.println("Choose an option:");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println("║ 1. Insert new items manually (Type 0 in the POID to exit)");
            System.out.println("║ 2. List items");
            System.out.println("║ 3. Delete items");
            System.out.println("║ 4. Update items");
            if (documentsCount > registrosMySQL) {
                System.out.println("║ 5. Synchronize database");
            }
            System.out.println("║ 6. Import items (Only XML)");
            System.out.println("║ 7. Export items (Only XML)");
            System.out.println("║ 8. Back to database selection...");
            System.out.println(" ══════════════════════════════════════════════════════════\n");


            option = sc.nextLine();

            switch (option) {
                case "1":
                    insert(conn, database, collection);
                    Tools.pressAnyKeyToContinue();
                    break;
                case "2":
                    int opcion2;
                    System.out.println("=======================================");
                    System.out.println("|              List Menu              |");
                    System.out.println("=======================================");
                    System.out.println("|     1. List all items               |");
                    System.out.println("|     2. List item by POID            |");
                    System.out.println("|     3. List items by different POIDS|");
                    System.out.println("=======================================");

                    opcion2 = getIntInput(sc);

                    switch (opcion2) {
                        case 1: {
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            List<Poi> newpois = UserMongoDAO.listPoisArray(conn, database, collection);
                            int count = 0;
                            for (Poi poi : newpois) {
                                System.out.println(poi);
                                count++;

                                if (count % 4 == 0) {
                                    Tools.pressAnyKeyToContinue(); //Ask to press any key to continue
                                }
                            }
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 2: {
                            System.out.println("Type the poid the Point of Interest");
                            int poid = getIntInput(sc);
                            if (poiExistsForMongoDB(conn, poid, database, collection)) {
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                                System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");

                                System.out.println(UserMongoDAO.listsSinglePoi(conn, database, collection, poid));
                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            } else {
                                System.out.println("This poid doesn't exist");
                            }
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 3: {
                            List<Integer> poidList = askAndStorePoidsInArray();

                            List<Poi> selectedPois = listMultiplePois(conn, database, collection, poidList);

                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            System.out.println("| POID  | Latitude  | Longitude  |       Country        |         City         |      Description     |       Updated        |");
                            System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");

                            int count = 0;
                            for (int poid : poidList) {
                                if (poiExistsForMongoDB(conn, poid, database, collection)) {
                                    System.out.println(UserMongoDAO.listsSinglePoi(conn, database, collection, poid));
                                    count++;

                                    if (count % 4 == 0) {
                                        Tools.pressAnyKeyToContinue();
                                    }
                                } else {
                                    System.out.println("POID " + poid + " doesn't exist in the database.");
                                }

                                System.out.println("+-------+-----------+------------+----------------------+----------------------+----------------------+----------------------+");
                            }

                            if (count % 4 != 0) {
                                Tools.pressAnyKeyToContinue();
                            }
                            break;
                        }
                        default: {
                            System.out.println("Invalid option. Please enter a valid option");
                        }
                    }
                    break;
                case "3":
                    int opcion3;
                    System.out.println("=======================================");
                    System.out.println("|            Delete Menu               |");
                    System.out.println("=======================================");
                    System.out.println("|    1. Delete all items              |");
                    System.out.println("|    2. Delete item by POID           |");
                    System.out.println("|    3. Delete items by different POIDS|");
                    System.out.println("=======================================");
                    opcion3 = getIntInput(sc);

                    switch (opcion3) {
                        case 1: {
                            String choice;
                            System.out.println("Are you sure that you want to delete all data? (Yes/No)");
                            do {
                                choice = sc.nextLine().toLowerCase();
                                switch (choice) {
                                    case "yes":
                                        UserMongoDAO.deleteAllDocuments(conn, database, collection);
                                        break;
                                    case "no":
                                        System.out.println("Going back...");
                                        break;
                                    default:
                                        System.out.println("Invalid input. Please enter Yes or No.");
                                        break;
                                }
                            } while (!choice.equals("yes") && !choice.equals("no"));
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 2: {
                            System.out.println("Type the poid to delete the Point of Interest");
                            int poid = getIntInput(sc);

                            if (poiExistsForMongoDB(conn, poid, database, collection)) {
                                UserMongoDAO.deleteSinglePoi(conn, database, collection, poid);
                                System.out.println("Point of interest deleted succesfully");
                            } else {
                                System.out.println("Oooopsidupsi!!! This poid doesn't exist in database");
                            }
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        case 3: {
                            List<Integer> poidList = askAndStorePoidsInArray();
                            List<Integer> deletedPoids = UserMongoDAO.deleteMultiplePois(conn, database, collection, poidList);

                            if (deletedPoids.size() == poidList.size()) {
                                System.out.println("All pois were deleted");
                            } else {
                                System.out.println("Not all poids were deleted successfully, because they don't exist Deleted POIDs: " + deletedPoids);
                            }
                            Tools.pressAnyKeyToContinue();
                            break;
                        }
                        default: {
                            System.out.println("Invalid option. Please enter a valid option");
                        }
                    }
                    break;
                case "4":
                    System.out.print("Ingrese el POID a verificar: ");
                    int poid = getIntInput(sc);

                    try {
                        if (poiExistsForMongoDB(conn,  poid,database, collection)) {
                            Poi newPoid = createPOI(sc, poid);
                            upsertSinglePoi(conn, database, collection, newPoid);
                            
                        } else {
                            System.out.println("El POID NO existe en la base de datos.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    if(documentsCount > registrosMySQL){
                        String choice;
                        System.out.println("Are you sure that you want to syinchronize this database with mysql?(This will delete all from database)");
                        do {
                            choice = sc.nextLine().toLowerCase();
                            switch (choice) {
                                case "yes":
                                    List<Poi> listapois = UserMongoDAO.listPoisArray(conn, database, collection);
                                    ;
                                    synchronizeMySQL(listapois);
                                    break;
                                case "no":
                                    System.out.println("Going back...");
                                    break;
                                default:
                                    System.out.println("Invalid input. Please enter Yes or No.");
                                    break;
                            }
                        } while (!choice.equals("yes") && !choice.equals("no"));
                        Tools.pressAnyKeyToContinue();
                    }else{
                        System.out.println("Invalid option. Please choose a valid option.");
                    }
                    break;

                case "6":
                    String ruta;
                    System.out.println("Por favor especifica la ruta del archivo que desea importar");
                    ruta = sc.nextLine();
                    
                    List<Poi> newPoisToImport;  
                    newPoisToImport = ImportXML.cargarListaPOI(ruta);
                    System.out.println("=======================================");
                        System.out.println("This data will be imported:");
                        System.out.println("=======================================");
                    
                    for (Poi pois : newPoisToImport) {
                        System.out.println(pois);
                    }
                    System.out.println();
                    
                    if (!newPoisToImport.isEmpty()) {
                        System.out.println("Type 'add' to add this data or 'delete' if you want to delete all data from the database and then add this one");
                        String choice2;
                        do {
                            choice2 = sc.nextLine().toLowerCase();
                            switch (choice2) {
                                case "add":
                                    for (Poi pois : newPoisToImport) {
                                        insertSingleDocument(conn, database, collection, pois);
                                    }   break;
                                case "delete":
                                    System.out.println("We will proceed to delete all data from the database and import the new one...");
                                    UserMongoDAO.deleteAllDocuments(conn, database, collection);
                                    for (Poi pois : newPoisToImport) {
                                        insertSingleDocument(conn, database, collection, pois);
                                    }   break;
                                default:
                                    System.out.println("Invalid input. Please enter 'add' or 'delete'.");
                                    break;
                            }
                        } while (!choice2.equals("add") && !choice2.equals("delete"));
                    } else {
                        System.out.println("=======================================");
                        System.out.println("The file you want to import is empty!!!");
                        System.out.println("=======================================");
                    }    
                    Tools.pressAnyKeyToContinue();
                    break;

                case "7":
                    if(guardarXMLpois(UserMongoDAO.listPoisArray(conn, database, collection))){
                        System.out.println("File exported succesfully! Look in your desktop.");
                    }
                    break;
                case "8":
                    System.out.println("Returning to the database selection.");
                    break;
                default: {
                    System.out.println("Invalid option. Please choose a valid option.");
                }
            }
        } while (!option.equals("8"));

    }

    public static void main(String[] args) throws SQLException, IOException {
        String databaseType;
        String databaseMongoDB;
        String collectionMongoDB;

        System.out.println(getRandomWelcomeMessage());
        System.out.println(welcomeMessageExplanation);
        System.out.println();

        do {
            System.out.println("Select the database you want to use (MongoDB, MySQL) or exit to finish:");
            databaseType = sc.nextLine().toLowerCase();

            switch (databaseType) {
                case "mongodb":
                    conn = connectToMongoClient();
                    System.out.println(" --------------------");
                    System.out.println("|                    |");
                    System.out.println("| Welcome to MongoDB |");
                    System.out.println("|                    |");
                    System.out.println(" --------------------");

                    System.out.println();
                    System.out.println("Now select the database you want to use or type a new one to crate it:\n"
                            + "--------------------------------------------");
                    printDatabases(conn);
                    databaseMongoDB = sc.nextLine();
                    System.out.println();
                    MongoDatabase mongoDB = selectDBMongo(conn, databaseMongoDB);
                    printCollections(mongoDB);
                    System.out.println();
                    System.out.println("Now select the database you want to use or type a new one to crate it:\n"
                            + "--------------------------------------------");
                    collectionMongoDB = sc.nextLine();
                    useDBMongo(conn, databaseMongoDB, collectionMongoDB);
                    runMongoDBMenu(conn, databaseMongoDB, collectionMongoDB);
                    break;
                case "mysql":
                    System.out.println("Connecting to MySQL");
                    waitTimeUser();
                    System.out.println(" --------------------");
                    System.out.println("|                    |");
                    System.out.println("|  Welcome to Mysql  |");
                    System.out.println("|                    |");
                    System.out.println(" --------------------");
                    runMySQLMenu("Poidb","pois_IE28");
                    break;

                case "exit":
                    System.out.println("Exiting the database manager. Goodbye!");
                    closeMongoClient(conn);
                    break;

                default:
                    System.out.println("Option not valid. Please select MongoDB, MySQL, or exit.");
            }
        } while (!databaseType.equals("exit"));
    }

}
