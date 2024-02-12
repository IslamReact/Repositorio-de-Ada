/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ModuleDAO;
import Entity.Enrolment;
import Entity.ModuleFP;
import static Tools.Tools.getIntInput;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

/**
 *
 * @author islam
 */
public class ModuleController {
    
    /**
     * 
     * @param module
     * @param enrolments 
     */
    public static void listEnrolmentsRealtionatedWithModule(ModuleFP module, List<Enrolment> enrolments){
        enrolments = module.getEnrolments();
        System.out.println("↘ ");
        if (enrolments != null && !enrolments.isEmpty()) {
            System.out.print("Enrolments: ");
            System.out.println("+---------------------------------------------------------------------------+");
            for (Enrolment enrolment : enrolments) {
                System.out.println("            " + enrolment.getStudent());
            }
            System.out.println("            +---------------------------------------------------------------------------+");
        } else {
            System.out.println("  No enrolments in this module.");
        }
        System.out.println("+-------------------------------------------+");
    }
    
    /**
     * 
     * @param em
     * @param scanner
     * @return 
     */
    public static ModuleFP askForModule(EntityManager em, Scanner scanner) {
        ModuleDAO moduleDAO = new ModuleDAO(em);
        List<ModuleFP> moduleList = moduleDAO.selectAll();

        System.out.println("\nAvailable Modules: ⬇️");
        displayModuleList(moduleList);

        while (true) {
            System.out.print("\nEnter the code of the module: ");
            int moduleCode = getIntInput(scanner);

            ModuleFP selectedModule = moduleDAO.getModuleById(moduleCode);

            if (selectedModule != null) {
                return selectedModule;
            } else {
                System.out.println("[❌]  This module doesn't exist. Please enter a valid module code.");
            }
        }
    }
    /**
     * 
     * @param moduleList 
     */
    public static void displayModuleList(List<ModuleFP> moduleList) {
        for (ModuleFP module : moduleList) {
            System.out.println(module.toString());
        }
    }

}
