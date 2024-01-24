/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author islam
 */
public class Adresa {
    private int adresaID;
    private int codi_Postal;
    private String provincia;
    private String carrer;
    private int numCarrer;
    private String cif; 

    public Adresa(int codi_Postal, String provincia, String carrer, int numCarrer, String cif) {
        this.codi_Postal = codi_Postal;
        this.provincia = provincia;
        this.carrer = carrer;
        this.numCarrer = numCarrer;
        this.cif = cif;
    }

    public Adresa(int adresaID, int codi_Postal, String provincia, String carrer, int numCarrer, String cif) {
        this.adresaID = adresaID;
        this.codi_Postal = codi_Postal;
        this.provincia = provincia;
        this.carrer = carrer;
        this.numCarrer = numCarrer;
        this.cif = cif;
    }

    
    public int getAdresaID() {
        return adresaID;
    }

    public void setAdresaID(int adresaID) {
        this.adresaID = adresaID;
    }

    public int getCodi_Postal() {
        return codi_Postal;
    }

    public void setCodi_Postal(int codi_Postal) {
        this.codi_Postal = codi_Postal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }

    public int getNumCarrer() {
        return numCarrer;
    }

    public void setNumCarrer(int numCarrer) {
        this.numCarrer = numCarrer;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    @Override
    public String toString() {
        return "Adresa -> AdresaID= " + adresaID + ", codi_Postal= " + codi_Postal + ", Provincia= " + provincia + ", Carrer= " + carrer + ", numCarrer= " + numCarrer;
    }
}