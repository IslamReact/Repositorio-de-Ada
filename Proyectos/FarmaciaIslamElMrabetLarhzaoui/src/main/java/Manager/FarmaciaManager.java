/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import ConexionBD.AdresaDAO;
import ConexionBD.FarmaciaDAO;
import Domain.Adresa;
import Domain.Farmacia;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author islam
 */
public class FarmaciaManager { 
    public static Scanner sc = new Scanner(System.in);
    public static void insertarFarmaciaYAdresa(String cif, Adresa adresa) throws SQLException {
        try {
            // Crear una instancia de Farmacia con adresaID temporal
            Farmacia farmacia = new Farmacia(cif);
            farmacia.setAdresaID(-1); // Valor temporal

            // Insertar la Farmacia en la base de datos y obtener su CIF
            int registrosInsertadosFarmacia = FarmaciaDAO.insertar(farmacia);

            if (registrosInsertadosFarmacia > 0) {
                // Obtener el CIF de la Farmacia creada
                String cifFarmacia = farmacia.getCif();

                // Insertar la Farmacia en la base de datos y obtener el adresaID real
                int adresaID = FarmaciaDAO.obtenerAdresaID(cifFarmacia); // Debe implementar esta función en FarmaciaDAO

                if (adresaID > -2) {
                    // Luego, asignar el adresaID real a la instancia de Adresa
                    adresa.setCif(cifFarmacia);
                    adresa.setAdresaID(adresaID);

                    // Insertar la Adresa en la base de datos
                    int registrosInsertadosAdresa = AdresaDAO.insertar(adresa);

                    if (registrosInsertadosAdresa > 0) {
                        System.out.println("Farmacia y Adresa insertadas correctamente.");
                    } else {
                        System.out.println("Error al insertar la Adresa.");
                    }
                } else {
                    System.out.println("Error al obtener el adresaID.");
                }
            } else {
                System.out.println("Error al insertar la Farmacia.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}



