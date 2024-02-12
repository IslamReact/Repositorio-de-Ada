/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tools;

import Entity.Student;
import Entity.Group;
import Entity.ModuleFP;
import static Controller.GroupController.askForGroup;
import static Controller.StudentController.askForStudent;
import DAO.ProjectDAO;
import DAO.StudentDAO;
import Entity.Project;
import static Tools.Tools.getIntInput;
import java.util.ArrayList;
import java.util.Scanner;
import javax.persistence.EntityManager;

/**
 *
 * @author islam
 */
public class Menus {
    
    /*
    * This class will contain menus, headers, and data asking.
    *
    * First section: Menus.
    * ----------------------------------------------------------------------------------------------------------------------
    *
    */
    
    /**
     * 
     */
    public static void showTableMenu() { 
        System.out.println("           ╔═══════════════════════════════╗");
        System.out.println("           ║        Select a table         ║");
        System.out.println("           ║═══════════════════════════════║");
        System.out.println("           ║ 1. Student                    ║");
        System.out.println("           ║ 2. Enrolment                  ║");
        System.out.println("           ║ 3. Module                     ║");
        System.out.println("           ║ 4. Project                    ║");
        System.out.println("           ║ 5. Group                      ║");
        System.out.println("           ║ 0. EXIT                       ║");
        System.out.println("           ╚═══════════════════════════════╝");
        System.out.print("Enter table option: ");
    }
    
    /**
     * 
     */
    public static void showMainMenu(){
        System.out.println("            ╔═══════════ Menu ═══════════╗");
        System.out.println("            ║ 1. Empty All Tables        ║");
        System.out.println("            ║ 2. Add New Elements        ║");
        System.out.println("            ║ 3. List Elements           ║");
        System.out.println("            ║ 4. Delete Items            ║");
        System.out.println("            ║ 0. EXIT                    ║");
        System.out.println("            ╚════════════════════════════╝");
        System.out.print("Select an option: ");
    }
    
    /**
     * 
     * @param isAviable
     */
    public static void getDeleteOption(Boolean isAviable) {
        System.out.println("           ╔═══════════ Delete ═════════════╗");
        System.out.println("           ║ 1. Delete different items      ║");
        if(isAviable){System.out.println( "           ║ 2. Delete all items            ║");}
        System.out.println("           ║ 0. EXIT                        ║");
        System.out.println("           ╚════════════════════════════════╝");
        System.out.print("Select an option: ");
        
    }
    
    /**
     * 
     */
    public static void selectElementsListOption() {
        System.out.println("            ╔════════════ List ════════════╗");
        System.out.println("            ║ 1. List multiple elements    ║");
        System.out.println("            ║ 2. List all element          ║");
        System.out.println("            ║ 0. EXIT .                    ║");
        System.out.println("            ╚══════════════════════════════╝");
        System.out.print("Select an option: "); 
    }
        
    
    /*
    *
    *
    *Second section: headers.
    *----------------------------------------------------------------------------------------------------------------------
    *
    */
    
    /**
     * 
     * @param groupCount
     * @param studentCount
     * @param enrolmentCount
     * @param projectCount
     * @param moduleCount 
     */
    public static void displayElementCounts(int groupCount, Long studentCount, Long enrolmentCount, int projectCount, int moduleCount) {
        System.out.println("┌────────────────── Element Counts ────────────────────────┐");
        System.out.printf("│ Group: %d Student: %d Enrolment: %d Project: %d Module: %d    │%n",
                groupCount, studentCount, enrolmentCount, projectCount, moduleCount);
        System.out.println("└──────────────────────────────────────────────────────────┘");
    }
    
    public static void groupHeader(){
        System.out.println("+-------------+--------------+--------------+");
        System.out.println("| Group Code  | Description  | Classroom    |");
        System.out.println("+-------------+--------------+--------------+");
    }
    
    public static void studentHeader(){
        System.out.println("+-------------+--------------+-----------------------+----------------------+");
        System.out.println("|    Nia      |     Name     |        Surnames       |   Group Description  |");
        System.out.println("+-------------+--------------+-----------------------+----------------------+");
    }
    
    public static void moduleHeader(){
        System.out.println("+-------------+--------------+--------------+");
        System.out.println("| Module Code | Description  |     Hours    |");
        System.out.println("+-------------+--------------+--------------+");
    }
    
    public static void projectHeader() {
        System.out.println("+------------------+--------------------------+---------------------+");
        System.out.println("| Código Proyecto  |        Título            |  NIA del Estudiante |");
        System.out.println("+------------------+--------------------------+---------------------+");
    }
    
    public static void enrolmentHeader() {
        System.out.println("+------------------+----------------------+---------------------+");
        System.out.println("|   Student Name   |    Module Name       |        Hours        |");
        System.out.println("+------------------+----------------------+---------------------+");
    }

    
    /*
    *
    *
    *Third section: Data asking.
    *----------------------------------------------------------------------------------------------------------------------
    *
    */
    
    /**
     * 
     * @param scanner
     * 
     * @return Group
     */
    public static Group askForGroupData(Scanner scanner) {
        System.out.print("Type the description of this group (enter '0' to stop): ");
        String description = scanner.nextLine();

        if ("0".equals(description)) {
            return null; // Signal to stop inserting groups
        }

        System.out.print("Type the classroom of this group: ");
        String classroom = scanner.nextLine();

        return new Group(description, classroom);
    }
    
    /**
     * 
     * @param scanner
     * 
     * @return ModuleFP 
     */
    public static ModuleFP askForModuleData(Scanner scanner) {
        
        System.out.print("Type the name of this Module (enter '0' to stop): ");
        String description = scanner.nextLine();

        if ("0".equals(description)) {
            return null; // Signal to stop inserting modules
        }

        System.out.print("Type the hours of this module: ");
        int hours = getIntInput(scanner);

        return new ModuleFP(description, hours);
    }
    
    /**
     * 
     * @param em
     * @param scanner
     * 
     * @return Student
     */
    public static Student askDataStudent(EntityManager em, Scanner scanner) {

        System.out.print("Enter student NIA(Type 0 to exit): ");
        String niaInput = scanner.nextLine().trim();

        // Validate NIA is 8 digits
        while (!niaInput.equals("0") && niaInput.length() != 8) {
            System.out.println("[❌]  NIA must be 8 digits. Please enter a valid NIA.");
            System.out.print("Enter student NIA(Type 0 to exit): ");
            niaInput = scanner.nextLine().trim();
        }

        if ("0".equals(niaInput)) {
            return null;
        }

        int nia = Integer.parseInt(niaInput);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter student surnames: ");
        String surnames = scanner.nextLine().trim();

        Group group = askForGroup(em, scanner);

        return new Student(nia, name, surnames, group);
    }       
    
    /**
     * 
     * @param em
     * @param scanner
     * @param studentDAO
     * @param projectDAO
     * @return Project
     */
    public static Project askForProjectData(EntityManager em, Scanner scanner,StudentDAO studentDAO, ProjectDAO projectDAO) {
        System.out.println("Type the project Data:");

        System.out.print("Title (Type 0 to exit): ");
        String title = scanner.nextLine(); 
        
        if("0".equals(title)){
            return null;
        }

        Student student = askForStudent(em,scanner);
        
        if (projectDAO.hasProjectForStudent(student)) {
            System.out.println("[❌]   Error: The selected student already has a project assigned.");
            return null;
        }

        return new Project(title, student);
    }
    
    public static ArrayList<Integer> askAndStoreIDInArray(Scanner sc, boolean isStudent) {
        ArrayList<Integer> ids = new ArrayList<>();
        int id;

        if (isStudent) {
            System.out.println("Enter one by one all Nia's you want to list (Type 0 to finish)");
        } else {
            System.out.println("Enter one by one all ID's you want to list (Type 0 to finish)");
        }

        do {
            id = getIntInput(sc);

            if (id != 0) {
                if (isStudent) {
                    if (isValidNIA(id)) {
                        ids.add(id);
                    } else {
                        System.out.println("[❌]  NIA must be 8 digits. Please enter a valid NIA.");
                    }
                } else {
                    ids.add(id);
                }
            }else{
                break;
            }
        } while (id != 0);

        return ids;
    }

    private static boolean isValidNIA(int nia) {
        String niaStr = String.valueOf(nia);
        return niaStr.length() == 8 && niaStr.matches("\\d+");
    }
   
    /*
    *
    *
    *Fifth section: INFO.
    *----------------------------------------------------------------------------------------------------------------------
    *
    */
    
    /**
     * 
     */
    public static void printGoodbyeArt() {
        System.out.println(" GGGG   OOO   OOO  DDDD   BBBBB   Y   Y  EEEEE ");
        System.out.println("G      O   O O   O D   D  B    B   Y Y   E     ");
        System.out.println("G  GG  O   O O   O D   D  BBBBB     Y    EEEE  ");
        System.out.println("G   G  O   O O   O D   D  B    B    Y    E     ");
        System.out.println(" GGGG   OOO   OOO  DDDD   BBBBB     Y    EEEEE ");
    }
}
