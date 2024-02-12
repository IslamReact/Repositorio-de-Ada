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
@Table(name = "PROYECTO_CONVOCATORIA_IE28")
@NamedQueries({
    @NamedQuery(name = "Project.SelectAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.DeleteAll", query = "DELETE FROM Project p"),
    @NamedQuery(name = "Project.CountAll", query = "SELECT COUNT(p) FROM Project p"),
    @NamedQuery(name = "Project.HasProjectForStudent", query = "SELECT COUNT(p) FROM Project p WHERE p.student = :student")
})
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODPROYECTO")
    private int codProject;

    @Column(name = "TITULO")
    private String title;

    @OneToOne
    @JoinColumn(name = "NIA", unique = true)
    private Student student;

    public Project(int codProject, String title, Student student) {
        this.codProject = codProject;
        this.title = title;
        this.student = student;
    }

    public Project(String title, Student student) {
        this.title = title;
        this.student = student;
    }

    public Project() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getCodProject() {
        return codProject;
    }

    public void setCodProject(int codProject) {
        this.codProject = codProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("| %-17d| %-21s| %-20s|",
                codProject,
                title,
                student != null ? student.getNia() : "N/A");
    }

}
