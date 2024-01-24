/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.StudentDAO;
import Entity.Enrolment;
import Entity.Student;
import Entity.Group;
import Entity.Project;
import static Tools.Tools.getIntInput;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

/**
 *
 * @author islam
 */
public class StudentController {
    /**
     * 
     * @param group
     * @param students 
     */
    public static void listStudentsRealtionatedWithGroup(Group group, List<Student> students){
        students = group.getStudents();
        System.out.println("↘ ");
        if (students != null && !students.isEmpty()) {
            System.out.print("Students: ");
            System.out.println("+---------------------------------------------------------------------------+");
            for (Student student : students) {
                System.out.println("          " + student);
            }
            System.out.println("          +---------------------------------------------------------------------------+");
        } else {
            System.out.println("  No students in this group.");
        }
        System.out.println("+-------------------------------------------+");
    }
    
    /**
     * 
     * @param student 
     * @param enrolments 
     */
    public static void listEnrolmentsAndProjectsForStudent(Student student, List<Enrolment> enrolments) {
        System.out.println("↘                                                                           |");
        if (!enrolments.isEmpty()) {
            System.out.print("Enrolments and Projects:");
            System.out.println(" +-------------------------------------------------------------------------+");
            for (Enrolment enrolment : enrolments) {
                System.out.println("                         |Enrolment" + enrolment.toString());
            }
        } else {
            System.out.println("No enrolments for this student.");
        }
        Project project = student.getProject();
        if (project != null) {
            System.out.println("                         +-------------------------------------------------------------------------+");
            System.out.println("                         |Project  " + project.toString());
            System.out.println("                         +-------------------------------------------------------------------------+");
        } else {
            System.out.println("No associated project.");
        }
    }
    
    /**
     * 
     * @param em
     * @param scanner
     * @return Student
     */
    public static Student askForStudent(EntityManager em, Scanner scanner) {
        StudentDAO studentDAO = new StudentDAO(em);
        List<Student> studentList = studentDAO.getAllStudents();

        System.out.println("Available Students:");
        displayStudentList(studentList);

        while (true) {
            System.out.print("Enter the NIA of the student: ");
            int studentNIA = getIntInput(scanner);

            Student selectedStudent = studentDAO.getStudentById(studentNIA);

            if (selectedStudent != null) {
                return selectedStudent;
            } else {
                System.out.println("This student doesn't exist. Please enter a valid NIA.");
            }
        }
    }

    /**
     * 
     * @param studentList 
     */
    private static void displayStudentList(List<Student> studentList) {
        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }

    /**
     * 
     * @param project
     * @param student 
     */
    public static void listStudentsRealtionatedWithProjects(Project project, Student student){
        student = project.getStudent();
        System.out.println("↘                                                               |");
        if (student != null) {
            System.out.print("Maker:");
            System.out.println("    +---------------------------------------------------------------------------+");
            System.out.println("|         " + student);
            System.out.println("|         +---------------------------------------------------------------------------+");
        } else {
            System.out.println("  No students in this group.");
        }
        System.out.println("+------------------+----------------------+---------------------+");
    }
}
