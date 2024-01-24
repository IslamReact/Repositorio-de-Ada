/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Enrolment;
import Entity.ModuleFP;
import java.util.List;

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
}
