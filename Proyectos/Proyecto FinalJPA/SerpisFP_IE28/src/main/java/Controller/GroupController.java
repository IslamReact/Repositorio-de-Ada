/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.GroupDAO;
import Entity.Group;
import static Tools.Tools.getIntInput;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

/**
 *
 * @author islam
 */
public class GroupController {
    
    public static Group askForGroup(EntityManager em, Scanner scanner) {
        GroupDAO groupDAO = new GroupDAO(em);
        List<Group> groupList = groupDAO.selectAll();

        System.out.println("Available Groups:");
        displayGroupList(groupList);

        while (true) {
            System.out.print("Enter the code of the group for the student: ");
            int groupCode = getIntInput(scanner);

            Group selectedGroup = groupDAO.selectGroupById(groupCode);

            if (selectedGroup != null) {
                return selectedGroup;
            } else {
                System.out.println("[❌]  This group doesn't exist. Exiting...");
                return null;
            }
        }
    }
    
    private static void displayGroupList(List<Group> groupList) {
        for (Group group : groupList) {
            System.out.println(group.toString());
        }
    }
}
