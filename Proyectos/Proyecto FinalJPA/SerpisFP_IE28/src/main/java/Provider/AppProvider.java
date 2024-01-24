/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Provider;

import DAO.*;
import javax.persistence.EntityManager;

/**
 * The AppProvider class serves as a provider for Data Access Object (DAO)
 * instances used in the application. It encapsulates the logic for creating
 * instances of various DAO classes associated with different entities. By
 * centralizing DAO creation logic, this class promotes code organization and
 * maintainability.
 *
 * @author Islam
 */
public class AppProvider {

    /**
     *
     * @param em
     * @return StudentDAO
     */
    public static StudentDAO getStudentDAO(EntityManager em) {
        return new StudentDAO(em);
    }

    /**
     *
     * @param em
     * @return EnrolmentDAO
     */
    public static EnrolmentDAO getEnrolmentDAO(EntityManager em) {
        return new EnrolmentDAO(em);
    }

    /**
     *
     * @param em
     * @return ProjectDAO
     */
    public static ProjectDAO getProjectDAO(EntityManager em) {
        return new ProjectDAO(em);
    }

    /**
     *
     * @param em
     * @return GroupDAO
     */
    public static GroupDAO getGroupDAO(EntityManager em) {
        return new GroupDAO(em);
    }

    /**
     *
     * @param em
     * @return ModuleDAO
     */
    public static ModuleDAO getModuleDAO(EntityManager em) {
        return new ModuleDAO(em);
    }
}
