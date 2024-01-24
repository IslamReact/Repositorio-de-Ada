/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Management.FilesManagement;

import Domain.Poi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author islam
 */
public class ImportXML {
    public static ArrayList<Poi> cargarListaPOI(String ruta) throws IOException {
        ArrayList<Poi> pois = new ArrayList<>();
        File archivo = new File(ruta);
        
        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("El archivo seleccionado no es válido.");
            return pois;
        }

        try (BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "UTF-8"))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().startsWith("<POI>")) {
                    int poid = 0;
                    double latitude = 0.0;
                    double longitude = 0.0;
                    String country = "";
                    String city = "";
                    String description = "";
                    Date updated = null;

                    while ((linea = lector.readLine()) != null && !linea.trim().equalsIgnoreCase("</POI>")) {
                        if (linea.trim().startsWith("<poid>")) {
                            poid = Integer.parseInt(linea.substring(linea.indexOf(">") + 1, linea.indexOf("</poid>")).trim());
                        } else if (linea.trim().startsWith("<latitude>")) {
                            latitude = Double.parseDouble(linea.substring(linea.indexOf(">") + 1, linea.indexOf("</latitude>")).trim());
                        } else if (linea.trim().startsWith("<longitude>")) {
                            longitude = Double.parseDouble(linea.substring(linea.indexOf(">") + 1, linea.indexOf("</longitude>")).trim());
                        } else if (linea.trim().startsWith("<country>")) {
                            country = linea.substring(linea.indexOf(">") + 1, linea.indexOf("</country>")).trim();
                        } else if (linea.trim().startsWith("<city>")) {
                            city = linea.substring(linea.indexOf(">") + 1, linea.indexOf("</city>")).trim();
                        } else if (linea.trim().startsWith("<description>")) {
                            description = linea.substring(linea.indexOf(">") + 1, linea.indexOf("</description>")).trim();
                        } else if (linea.trim().startsWith("<updated>")) {
                             String updatedText = linea.substring(linea.indexOf(">") + 1, linea.indexOf("</updated>")).trim();
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                java.util.Date utilDate = inputFormat.parse(updatedText);
                                updated = new java.sql.Date(utilDate.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        
                        }
                    }

                    // Crear objeto Canciones y agregar a la lista
                    Poi poisImported = new Poi(poid, latitude, longitude, description, country, city, updated);
                    pois.add(poisImported);
                }
            }
        }

        return pois;
    }
}


