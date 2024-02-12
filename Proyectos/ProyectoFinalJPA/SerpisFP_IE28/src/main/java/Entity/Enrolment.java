/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author islam
 */

@Entity
@Table(name = "MATRICULA_IE28")
@NamedQueries({
    @NamedQuery(name = "Enrolment.SelectById", query = "SELECT e FROM Enrolment e WHERE e.student = :student AND e.module = :module"),
    @NamedQuery(name = "Enrolment.SelectAll", query = "SELECT e FROM Enrolment e ORDER BY e.student.nia"),
    @NamedQuery(name = "Enrolment.CountAll", query = "SELECT Count(e) FROM Enrolment e"),
    @NamedQuery(name = "Enrolment.getByStudentId", query = "SELECT e FROM Enrolment e WHERE e.student.nia = :studentId"),
    @NamedQuery(name = "Enrolment.Delete", query = "DELETE FROM Enrolment e WHERE e.student = :student AND e.module = :module")
})
public class Enrolment implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "NIA")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "CODMODULO")
    private ModuleFP module;

    public Enrolment(Student student, ModuleFP module) {
        this.student = student;
        this.module = module;
    }

    public Enrolment() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ModuleFP getModule() {
        return module;
    }

    public void setModule(ModuleFP module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return String.format("| %16s | %-20s | %-19s |",
                this.student.getName(),
                this.module.getDescription(),
                this.module.getHours());
    }
}
