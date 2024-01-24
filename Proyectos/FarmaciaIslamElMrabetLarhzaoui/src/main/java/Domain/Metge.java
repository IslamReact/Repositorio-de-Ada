/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author islam
 */
public class Metge {
    private int numCollegiat;
    private String especialitat;
    private String nom;
    private String cognoms;
    
    public Metge(){
        
    }

    public Metge(int numCollegiat) {
        this.numCollegiat = numCollegiat;
    }

    public Metge(String especialitat, String nom, String cognoms) {
        this.especialitat = especialitat;
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public Metge(int numCollegiat, String especialitat, String nom, String cognoms) {
        this.numCollegiat = numCollegiat;
        this.especialitat = especialitat;
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public int getNumCollegiat() {
        return numCollegiat;
    }

    public void setNumCollegiat(int numCollegiat) {
        this.numCollegiat = numCollegiat;
    }

    public String getEspecialitat() {
        return especialitat;
    }

    public void setEspecialitat(String especialitat) {
        this.especialitat = especialitat;
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
        return "Metge --> Num collegiat= " + numCollegiat + ", Especialitat= " + especialitat + ", Nom= " + nom + ", cognoms= " + cognoms;
    }
    
   
}
