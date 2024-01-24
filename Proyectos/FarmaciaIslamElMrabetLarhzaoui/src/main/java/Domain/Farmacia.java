/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author islam
 */
public class Farmacia {
    private String cif;
    private int adresaID;

    public Farmacia(String cif, int adresaID) {
        this.cif = cif;
        this.adresaID = adresaID;
    }

    public Farmacia() {
    
    }

    public Farmacia(String cif) {
        this.cif = cif;
    }
    
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public int getAdresaID() {
        return adresaID;
    }

    public void setAdresaID(int adresaID) {
        this.adresaID = adresaID;
    }

    @Override
    public String toString() {
        return "Farmacia cif= " + cif + ", adresa= " + adresaID;
    }

}
