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
import Entity.ModuleFP;
import Entity.Student;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class EnrolmentDAO {
    /**
     * This class is the controller of Enrollment
     * 
     * Doesn't need to implement a method to deleteAll because its implemented in class module by the operator Cascade.
     * When we delete a module it will delete the enrolment associated too.
     * 
     */
    private final EntityManager entityManager;

    /**
     * 
     * @param entityManager 
     */
    public EnrolmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
    * 
    * @param enrolment 
    */
    public void createEnrolment(Enrolment enrolment) {
       EntityTransaction transaction = entityManager.getTransaction();
       try {
           transaction.begin();
           entityManager.persist(enrolment);

           Student student = enrolment.getStudent();
           if (student != null) {
               student.getEnrolments().add(enrolment);
               entityManager.merge(student);
           }

           transaction.commit();
       } catch (Exception e) {
           if (transaction.isActive()) {
               transaction.rollback();
           }
       }
    }

    /**
     * 
     * @param student
     * @param module
     * 
     * @return Enrolment
     */
    public Enrolment getEnrolmentById(Student student, ModuleFP module) {
        TypedQuery<Enrolment> query = entityManager.createNamedQuery("Enrolment.SelectById", Enrolment.class);
        query.setParameter("student", student);
        query.setParameter("module", module);

        List<Enrolment> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
    *
     * @param studentId The ID of the student.
     * @return List of Enrolment objects for the specified student.
     */
    public Enrolment getEnrolmentsByStudentId(int studentId) {
        TypedQuery<Enrolment> query = entityManager.createNamedQuery("Enrolment.getByStudentId", Enrolment.class);
        query.setParameter("studentId", studentId);
        return query.getSingleResult();
    }

    /**
     * 
     * @return List<Enrolment>
     */
    public List<Enrolment> getAllEnrolments() {
        TypedQuery<Enrolment> query = entityManager.createNamedQuery("Enrolment.SelectAll", Enrolment.class);
        return query.getResultList();
    }
       
    /**
     * 
     * @return Long
     */
    public Long getEnrolmentCount() {
        TypedQuery<Long> query = entityManager.createNamedQuery("Enrolment.CountAll", Long.class);
        return query.getSingleResult();
    }
    
    /**
     * Delete an enrolment.
     *
     * @param enrolment 
     */
    public void deleteEnrolment(Enrolment enrolment) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(enrolment);
            
            Student student = enrolment.getStudent();
            if (student != null) {
                student.getEnrolments().remove(enrolment);
                entityManager.merge(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

}

