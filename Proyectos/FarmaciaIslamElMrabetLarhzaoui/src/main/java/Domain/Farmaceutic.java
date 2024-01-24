/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author islam
 */
public class Farmaceutic {
    private String dni;
    private String nom;
    private String cognoms;
    private int anyLlicenciatura;

    public Farmaceutic() {
    }

    public Farmaceutic(String nom, String cognoms, int anyLlicenciatura) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.anyLlicenciatura = anyLlicenciatura;
    }

    public Farmaceutic(String dni, String nom, String cognoms, int anyLlicenciatura) {
        this.dni = dni;
        this.nom = nom;
        this.cognoms = cognoms;
        this.anyLlicenciatura = anyLlicenciatura;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public int getAnyLlicenciatura() {
        return anyLlicenciatura;
    }

    public void setAnyLlicenciatura(int anyLlicenciatura) {
        this.anyLlicenciatura = anyLlicenciatura;
    }

    @Override
    public String toString() {
        return "Farmaceutic - > DNI= " + dni + ", Nom= " + nom + ", Cognoms= " + cognoms + ", AnycLlicenciatura= " + anyLlicenciatura ;
    }
    
    
    
}
