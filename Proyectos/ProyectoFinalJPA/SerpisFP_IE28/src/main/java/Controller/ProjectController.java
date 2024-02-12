/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Project;
import java.util.List;

/**
 *
 * @author islam
 */
public class ProjectController {
    
    /**
     * 
     * @param projectList
     */
    public static void displayProjectList(List<Project> projectList) {
        for (Project project : projectList) {
            System.out.println(project.toString());
        }
    }

}
