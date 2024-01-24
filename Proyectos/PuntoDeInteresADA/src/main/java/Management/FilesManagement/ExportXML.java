/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Management.FilesManagement;

import Domain.Poi;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author islam
 */
public class ExportXML {
   
    public static boolean guardarXMLpois(List<Poi> listapois) {
        /*
        * Static method: saves a file inn .xml that contains the array of pois
        */
        boolean isDone = true;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/islam/Desktop/pois.xml"))) {
            
            int index = 1;
            for (Poi pois : listapois){
                writer.write("<POI>");
                writer.newLine();
                writer.write("<poid>" + pois.getPoid() + "</poid>");
                writer.newLine();
                writer.write("<latitude>"+ pois.getLatitude() + "</latitude>");
                writer.newLine();
                writer.write("<longitude>"+ pois.getLongitude() + "</longitude>");
                writer.newLine();
                writer.write("<country>"+ pois.getCountry() + "</country>");
                writer.newLine();
                writer.write("<city>"+ pois.getCity() + "</city>");
                writer.newLine();
                writer.write("<description>"+ pois.getDescription() + "</description>");
                writer.newLine();
                writer.write("<updated>"+ pois.getUpdated() + "</updated>");
                writer.newLine();
                writer.write("</POI>");
                writer.newLine();
                index++;
            }
            
        } catch (IOException e) {

        }
        return true;
    }
}
