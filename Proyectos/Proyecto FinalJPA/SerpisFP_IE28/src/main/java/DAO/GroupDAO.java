/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Group;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author islam
 */
public class GroupDAO {

    private final EntityManager entityManager;

    /**
     * 
     * @param entityManager 
     */
    public GroupDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 
     * @return List<Group> 
     */
    public List<Group> selectAll() {
        Query query = entityManager.createNamedQuery("Group.SelectAll");
        return query.getResultList();
    }

    /**
     * 
     * @param log
     * @param groupToInsert 
     */
    public void insertGroup(Logger log, Group groupToInsert) {
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.persist(groupToInsert);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("");
        }
    }
    
    /**
     * 
     * @param groupId
     * @return Group
     */
    public Group selectGroupById(int groupId) {
        try {
            return entityManager.find(Group.class, groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param groupIdToDelete
     * @return boolean
     */
    public boolean deleteGroup(int groupIdToDelete) {
        boolean isDeleted = false;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            Group groupToDelete = entityManager.find(Group.class, groupIdToDelete);

            if (groupToDelete != null) {
                if (groupToDelete.getStudents() != null && !groupToDelete.getStudents().isEmpty()) {
                    throw new IllegalStateException("[❌]  Cannot delete group. The group contains students. Delete students first.");
                } else {
                    entityManager.remove(groupToDelete);
                    tx.commit();
                    isDeleted = true;
                }
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
    public int deleteAllGroups() {
        int deletedCount = 0;
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            Query query = entityManager.createNamedQuery("Group.DeleteAll");
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
     * @param groupIdToFind
     * @param newDescription
     * @param newClasroom 
     */
    public void update(long groupIdToFind, String newDescription, String newClasroom) {
        try {
            Group groupFinded = entityManager.find(Group.class, groupIdToFind);
            groupFinded.setDescription(newDescription);

            entityManager.getTransaction().begin();
            groupFinded = entityManager.merge(groupFinded);
            groupFinded.setClasroom(newClasroom);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return int
     */
    public int countAllGroups() {
        int count = 0;
        try {
            Query query = entityManager.createNamedQuery("Group.CountAll");
            count = ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
}
