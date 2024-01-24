/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author islam
 */
import Entity.Enrolment;
import Entity.Student;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.persistence.Query;

public class StudentDAO {

    private final EntityManager entityManager;

    /**
     * 
     * @param entityManager 
     */
    public StudentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 
     * @param student 
     */
    public void createStudent(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param nia
     * @return Student
     */
    public Student getStudentById(int nia) {
        return entityManager.find(Student.class, nia);
    }

    /**
     * 
     * @return List<Student>
     */
    public List<Student> getAllStudents() {
        TypedQuery<Student> query = entityManager.createNamedQuery("Student.SelectAll", Student.class);
        return query.getResultList();
    }

    /**
     * 
     * @return Long
     */
    public Long getStudentCount() {
        TypedQuery<Long> query = entityManager.createNamedQuery("Student.CountAll", Long.class);
        return query.getSingleResult();
    }
    
    /**
     *
     * @return Long 
     */
    public Long getCountStudentsWithoutProjects() {
       TypedQuery<Long> query = entityManager.createNamedQuery("Student.CountWithoutProjects", Long.class);
       return query.getSingleResult();
    }

    /**
     * 
     * @param studentNIA
     * @return boolean
     */
    public boolean deleteStudent(int studentNIA) {
        boolean isDeleted = false;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            Student studentToDelete = entityManager.find(Student.class, studentNIA);

            if (studentToDelete != null) {
                if (!studentToDelete.getEnrolments().isEmpty() && !hasFCTEnrolment(studentToDelete.getEnrolments())) {
                    throw new IllegalStateException("[❌] Cannot delete student. Active enrolments exist.");
                }

                if (studentToDelete.getProject() != null) {
                    throw new IllegalStateException("[❌] Cannot delete student. The student is associated with a project.");
                }
                
                entityManager.remove(studentToDelete);
                tx.commit();
                isDeleted = true;
            } else {
                isDeleted = false;
            }
        } catch (IllegalStateException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println(e.getMessage());
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return isDeleted;
    }

    /**
     * 
     * @return int
     */
    public int deleteAllStudents() {
        int studentsDeletedCount = 0;
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createNamedQuery("Student.DeleteAll");
            studentsDeletedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        
        return studentsDeletedCount;
    }
    
    /**
     * 
     * @param enrolments
     * @return boolean
     */
    private boolean hasFCTEnrolment(List<Enrolment> enrolments) {
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getModule().getDescription().equals("FCT")) {
                return true;
            }
        }
        return false;
    }
}
