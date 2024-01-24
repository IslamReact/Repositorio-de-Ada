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
public class Ven {
    private String nomComercial;
    private String cif;
    private Date dates;
    private String quantitat;

    public Ven(String nomComercial, String cif, Date dates, String quantitat) {
        this.nomComercial = nomComercial;
        this.cif = cif;
        this.dates = dates;
        this.quantitat = quantitat;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public String getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(String quantitat) {
        this.quantitat = quantitat;
    }

    @Override
    public String toString() {
        return "Ven{" +
                "nomComercial='" + nomComercial + '\'' +
                ", cif='" + cif + '\'' +
                ", dates=" + dates +
                ", quantitat='" + quantitat + '\'' +
                '}';
    }
}