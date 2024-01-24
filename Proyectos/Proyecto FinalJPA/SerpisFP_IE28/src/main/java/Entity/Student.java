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
@Table(name = "ALUMNO_IE28")
@NamedQueries({
    @NamedQuery(name = "Student.SelectById", query = "SELECT s FROM Student s WHERE s.nia = :nia "),
    @NamedQuery(name = "Student.SelectAll", query = "SELECT s FROM Student s  "),
    @NamedQuery(name = "Student.DeleteAll", query = "DELETE FROM Student s"),
    @NamedQuery(name = "Student.CountWithoutProjects", query = "SELECT COUNT(s) FROM Student s WHERE s.project IS NULL"),
    @NamedQuery(name = "Student.CountAll", query = "SELECT COUNT(s) FROM Student s")
})
public class Student implements Serializable {

    @Id
    @Column(name = "NIA", unique = true)
    private int nia;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "APELLIDOS")
    private String surnames;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Project project;

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "CODGRUPO")
    private Group group;

    @OneToMany(mappedBy = "student")
    private List<Enrolment> enrolments;

    public Student(int nia, String nombre, String apellidos, Group group) {
        this.nia = nia;
        this.name = nombre;
        this.surnames = apellidos;
        this.group = group;
    }

    //Empty constructor(Required by JPA)
    public Student() {

    }

    public int getNia() {
        return nia;
    }

    public void setNia(int nia) {
        this.nia = nia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    @Override
    public String toString() {
        return String.format("| %11d | %-12s | %-21s | %-20s |",
                this.nia,
                this.name,
                this.surnames,
                this.group.getDescription());
    }

}
