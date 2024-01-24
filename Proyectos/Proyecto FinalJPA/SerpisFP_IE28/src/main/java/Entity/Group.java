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
@Table(name = "GRUPO_IE28")
@NamedQueries({
    @NamedQuery(name = "Group.SelectAll", query = "SELECT g FROM Group g"),
    @NamedQuery(name = "Group.DeleteAll", query = "DELETE FROM Group"),
    @NamedQuery(name = "Group.CountAll", query = "SELECT COUNT(g) FROM Group g")
})
public class Group implements Serializable {

    @Id
    @Column(name = "CODGRUPO")
    private int codGroup;
    @Column(name = "DESCRIPCION")
    private String description;
    @Column(name = "AULA")
    private String clasroom;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group(int coGroup, String description, String clasroom) {
        this.codGroup = coGroup;
        this.description = description;
        this.clasroom = clasroom;
    }

    public Group(String description, String clasroom) {
        this.description = description;
        this.clasroom = clasroom;
    }

    public Group() {

    }

    public int getCodGroup() {
        return codGroup;
    }

    public void setCodGroup(int codGroup) {
        this.codGroup = codGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClasroom() {
        return clasroom;
    }

    public void setClasroom(String clasroom) {
        this.clasroom = clasroom;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return String.format("| %11d | %-12s | %-12s |",
                this.codGroup,
                this.description,
                this.clasroom,
                this.getStudents());
    }

}
