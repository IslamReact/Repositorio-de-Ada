/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.logging.log4j.Logger;
import Entity.ModuleFP;

/**
 *
 * @author islam
 */
public class ModuleDAO {

    private final EntityManager entityManager;

    /**
     * 
     * @param entityManager 
     */
    public ModuleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 
     * @return List<ModuleFP>
     */
    public List<ModuleFP> selectAll() {
        Query query = entityManager.createNamedQuery("ModuleFP.SelectAll");
        return query.getResultList();
    }
    
    /**
     * 
     * @param cod
     * @return ModuleFP
     */
    public ModuleFP getModuleById(int cod) {
        return entityManager.find(ModuleFP.class, cod);
    }

    /**
     * 
     * @param log
     * @param moduleToInsert 
     */
    public void insertModule(Logger log, ModuleFP moduleToInsert) {
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.persist(moduleToInsert);
            tx.commit();
        } catch (javax.persistence.RollbackException e) {
            System.out.println("");
        }
    }

    /**
     * 
     * @param moduleIdToDelete
     * @return boolean
     */
    public boolean deleteModule(int moduleIdToDelete) {
        boolean isDeleted = false;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            ModuleFP moduleToDelete = entityManager.find(ModuleFP.class, moduleIdToDelete);

            if (moduleToDelete != null) {
                entityManager.remove(moduleToDelete);
                tx.commit();
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
    public int deleteAllModules() {
        int deletedCount = 0;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            Query query = entityManager.createNamedQuery("ModuleFP.DeleteAll");
            deletedCount = query.executeUpdate();
            tx.commit();
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
    public int countAllModules() {
        int count = 0;

        try {
            Query query = entityManager.createNamedQuery("ModuleFP.CountAll");
            count = ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
