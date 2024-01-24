/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.Date;

/**
 *
 * @author islam
 */
public class Prescriu {
    private String DNI;
    private int NumCollegiat;
    private String NomComercial;
    private Date Dates;
    private int Quantitat;

    public Prescriu(String DNI, int NumCollegiat, String NomComercial, Date Dates, int Quantitat) {
        this.DNI = DNI;
        this.NumCollegiat = NumCollegiat;
        this.NomComercial = NomComercial;
        this.Dates = Dates;
        this.Quantitat = Quantitat;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getNumCollegiat() {
        return NumCollegiat;
    }

    public void setNumCollegiat(int NumCollegiat) {
        this.NumCollegiat = NumCollegiat;
    }

    public String getNomComercial() {
        return NomComercial;
    }

    public void setNomComercial(String NomComercial) {
        this.NomComercial = NomComercial;
    }

    public Date getDates() {
        return Dates;
    }

    public void setDates(Date Dates) {
        this.Dates = Dates;
    }

    public int getQuantitat() {
        return Quantitat;
    }

    public void setQuantitat(int Quantitat) {
        this.Quantitat = Quantitat;
    }
}