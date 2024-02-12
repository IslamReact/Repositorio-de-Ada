/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Project;
import Entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author islam
 */
public class ProjectDAO {

    private final EntityManager entityManager;

    /**
     * Constructor.
     * 
     * @param entityManager 
     */
    public ProjectDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 
     * @return List<Project>
     */
    public List<Project> selectAll() {
        TypedQuery<Project> query = entityManager.createNamedQuery("Project.SelectAll", Project.class);
        return query.getResultList();
    }
    
    /**
     * 
     * @param projectId
     * 
     * @return Project
     */
    public Project selectProjectById(int projectId) {
        try {
            return entityManager.find(Project.class, projectId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param projectToInsert 
     */
    public void insertProject(Project projectToInsert) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(projectToInsert);
            tx.commit();
            entityManager.clear();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
    * 
    * @param projectIdToDelete
    * @return boolean
    */
   public boolean deleteProject(int projectIdToDelete) {
       boolean isDeleted = false;
       EntityTransaction tx = entityManager.getTransaction();

       try {
           if (!tx.isActive()) {
               tx.begin();
           }

           Project projectToDelete = entityManager.find(Project.class, projectIdToDelete);

           if (projectToDelete != null) {
                entityManager.refresh(entityManager.merge(projectToDelete));
                entityManager.remove(projectToDelete);
                
               if (tx.isActive()) {
                   tx.commit();
               }

               entityManager.clear();
               
               isDeleted = true;
           } else {
               isDeleted = false;
           }
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
    public int deleteAllProjects() {
        int deletedCount = 0;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            Query query = entityManager.createNamedQuery("Project.DeleteAll");
            deletedCount = query.executeUpdate();
            tx.commit();
            entityManager.clear();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return deletedCount;
    }

    /**
     * 
     * @return int
     */
    public int countAllProjects() {
        int count = 0;
        try {
            Query query = entityManager.createNamedQuery("Project.CountAll");
            count = ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
    /**
     * 
     * @param student
     * 
     * @return boolean
     */
    public boolean hasProjectForStudent(Student student) {
        try {
            Long count = entityManager.createNamedQuery("Project.HasProjectForStudent", Long.class)
                    .setParameter("student", student)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }
    
}
