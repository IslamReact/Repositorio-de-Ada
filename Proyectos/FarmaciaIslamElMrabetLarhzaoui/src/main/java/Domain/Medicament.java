/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author islam
 */
public class Medicament {
    private String Formula;
    private String nomComercial;

    public Medicament(String nomComercial,String Formula) {
        this.Formula = Formula;
        this.nomComercial = nomComercial;
    }

    public Medicament(String Formula) {
        this.Formula = Formula;
    }

    public String getFormula() {
        return Formula;
    }

    public void setFormula(String Formula) {
        this.Formula = Formula;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    @Override
    public String toString() {
        return "Medicament -> Formula= " + Formula + ", Nom comercial= " + nomComercial;
    }
    
    
}
