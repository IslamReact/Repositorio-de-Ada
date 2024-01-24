/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.bson.Document;

/**
 *
 * @author islam
 */
public class Poi {
   
    private int poid;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String description;
    private java.sql.Date updated;

    //Constructor
    public Poi(int poid, double latitude, double longitude, String country, String city, String description, java.sql.Date updated) {
        this.poid = poid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.description = description;
        this.updated = updated;
    }
    
    public Poi() {
        // Constructor vacio
    }

    // Getters and Setters
    public int getPoid() {
        return poid;
    }

    public void setPoid(int poid) {
        this.poid = poid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getUpdated() {
        return updated;
    }

    public void setUpdated(java.sql.Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-9.6f | %-10.6f | %-20s | %-20s | %-20s | %-20s |",
                this.poid,
                this.latitude,
                this.longitude,
                this.country,
                this.city,
                this.description,
                this.updated);
    }
    
    public static Poi convertDocumentToPOI(Document document) {
        Poi poi = new Poi();
        poi.setPoid(document.getInteger("poid"));
        poi.setLatitude(document.getDouble("latitude"));
        poi.setLongitude(document.getDouble("longitude"));
        poi.setCountry(document.getString("country"));
        poi.setCity(document.getString("city"));
        poi.setDescription(document.getString("description"));

        String updatedText = document.getString("updated");
        if (updatedText != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parsedDate = format.parse(updatedText);
                poi.setUpdated(new java.sql.Date(parsedDate.getTime())); // Convertimos a java.sql.Date
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            poi.setUpdated(null);
        }

        return poi;
    }
    
    public static Document convertPoiToDocument(Poi poi) {
        Document document = new Document();
        
        document.append("poid", poi.getPoid());
        document.append("latitude", poi.getLatitude());
        document.append("longitude", poi.getLongitude());
        document.append("country", poi.getCountry());
        document.append("city", poi.getCity());
        document.append("description", poi.getDescription());
        document.append("updated", poi.getUpdated());

        return document;
    }
}

