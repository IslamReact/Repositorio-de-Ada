/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;


/**
 *
 * @author islam
 */
import java.util.Date;

public class Tractapacient {
    private String DNI; // DNI del paciente
    private int NumCollegiat; // Número de colegiado del médico
    private Date Dates; // Fecha del tratamiento

    public Tractapacient(String DNI, int NumCollegiat, Date Dates) {
        this.DNI = DNI;
        this.NumCollegiat = NumCollegiat;
        this.Dates = Dates;
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

    public Date getDates() {
        return Dates;
    }

    public void setDates(Date Dates) {
        this.Dates = Dates;
    }

    @Override
    public String toString() {
        return "TractaPacient{" +
                "DNI='" + DNI + '\'' +
                ", NumCollegiat=" + NumCollegiat +
                ", Dates=" + Dates +
                '}';
    }
    
}
    

