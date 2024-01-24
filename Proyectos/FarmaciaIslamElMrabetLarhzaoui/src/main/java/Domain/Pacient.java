/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author islam
 */
public class Pacient {
    private String dNI;
    private String nom;
    private String cognoms;

    public Pacient() {
    }

    public Pacient(String nom, String cognoms) {
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public Pacient(String dNI, String nom, String cognoms) {
        this.dNI = dNI;
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public String getdNI() {
        return dNI;
    }

    public void setdNI(String dNI) {
        this.dNI = dNI;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    @Override
    public String toString() {
        return "Pacient -> DNI= " + dNI + ", Nom= " + nom + ", Cognoms= " + cognoms;
    }
    
    
}
