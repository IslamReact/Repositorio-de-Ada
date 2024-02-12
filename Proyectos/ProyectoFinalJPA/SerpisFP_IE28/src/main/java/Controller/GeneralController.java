/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.*;
import java.util.Scanner;

/**
 *
 * @author islam
 */
public class GeneralController {
    
    
    public static void handleDeleteAllDataOption(StudentDAO studentDAO, GroupDAO groupDAO, ModuleDAO moduleDAO, ProjectDAO projectDAO,Scanner scanner) {
        System.out.println("Are you sure that you want to delete all data? (Y/N)");
        String chooseOption = scanner.nextLine().trim().toLowerCase();

        if (chooseOption.equals("y")) {
            emptyAllTables(studentDAO, groupDAO, moduleDAO, projectDAO);
            System.out.println("[✅]  All data deleted successfully.");
        } else {
            System.out.println("[❌] . Operation canceled. No data deleted.");
        }
    }
    
    /**
     * 
     * EnrolmentDAO is not implemented because when we delete a sudent that has got an enrolment, it will automaticlly be deleted
     * 
     * @param studentDAO
     * @param groupDAO
     * @param moduleDAO 
     */
    private static void emptyAllTables(StudentDAO studentDAO,GroupDAO groupDAO,ModuleDAO moduleDAO, ProjectDAO projectDAO){
        
        moduleDAO.deleteAllModules();//DELETE ALL MODULES
        projectDAO.deleteAllProjects();//DELETE ALL PROJECTS
        studentDAO.deleteAllStudents();//DELETE ALL STUDENTS
        groupDAO.deleteAllGroups(); //DELETE ALL GROUPS
    }
}
