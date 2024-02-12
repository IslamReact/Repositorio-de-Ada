/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author islam
 */
@Entity
@Table(name = "MODULO_IE28")
@NamedQueries({
    @NamedQuery(name = "ModuleFP.SelectAll", query = "SELECT m FROM ModuleFP m"),
    @NamedQuery(name = "ModuleFP.DeleteAll", query = "DELETE FROM ModuleFP"),
    @NamedQuery(name = "ModuleFP.CountAll", query = "SELECT COUNT(m) FROM ModuleFP m")
})
public class ModuleFP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODMODULO")
    private int codModule;

    @Column(name = "DESCRIPCION")
    private String description;

    @Column(name = "NUMHORAS")
    private int hours;

    @OneToMany(mappedBy = "module")
    private List<Enrolment> enrolments;

    public ModuleFP(int codModule, String description, int hours) {
        this.codModule = codModule;
        this.description = description;
        this.hours = hours;
    }

    public ModuleFP(String description, int hours) {
        this.description = description;
        this.hours = hours;
    }

    public ModuleFP() {

    }

    public int getCodModule() {
        return codModule;
    }

    public void setCodModule(int codModule) {
        this.codModule = codModule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    @Override
    public String toString() {
        return String.format("| %11d | %-12s | %-12s |",
                this.codModule,
                this.description,
                this.hours);
    }

}
