/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tools;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Utility class providing common methods for handling user input, date conversion, and application flow.
 * These methods are designed to be static and can be used without creating an instance of the class.
 *
 * @author islam
 */
public class Tools {
    
    /**
     * 
     */
    public static void simulateConexionwaitTimeUser(){
        try {
            System.out.println("Connecting...");
            Thread.sleep(3000);
            System.out.println("[✅]  App succesfully connected with database!");
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }
    
    /**
     * 
     * @param sc
     * @return double
     * 
    */
    public static double readDouble(Scanner sc) {
        double value = 0.0;
        while (true) {
            String input = sc.nextLine();
            if (input.isEmpty()) {
                value = 0.0;
                break;
            }
            try {
                value = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[❌]  Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
    
    /**
     * 
     */
    public static void pressAnyKeyToContinue() {
        System.out.println(""); // Just a separator
        System.out.println("Press Enter key to continue...");
        try {
                System.in.read();
        } catch (IOException ex) {
                System.out.println("[❌]  Oooops! Something was wrong!");
                ex.printStackTrace(System.out);
        }
    }
    
    /**
     * 
     * @return String
     */
    public static String convertDateToString() {
        // get actual date
        LocalDate currentDate = LocalDate.now();

        // Format date to this format (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }
    
    /**
     * 
     * @param scanner
     * @return int
     */
    public static int getIntInput(Scanner scanner) {
        int value = 0;
        boolean validInput = false;
        do {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[❌]  Invalid input. Please enter an integer.");
                    continue;
                }
                value = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("[❌]  Invalid input. Please enter an integer.");
            }
        } while (!validInput);

        return value;
    }
    
    /**
     * 
     * @param scanner
     * @return long
     */
    public static long getLongInput(Scanner scanner) {
        long value = 0;
        boolean validInput = false;
        do {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[❌]  Invalid input. Please enter an integer.");
                    continue;
                }
                value = Long.parseLong(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("[❌]  Invalid input. Please enter an integer.");
            }
        } while (!validInput);

        return value;
    }
    
    /**
     * 
     * @param scanner
     * @param isStudent
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> askAndStoreGroupIdsInArray(Scanner scanner,Boolean isStudent) {
        int groupId;
        ArrayList<Integer> groupIds = new ArrayList<>();

        if(isStudent){ 
            System.out.println("Enter Nia's one by one (Type 0 to finish):");
        }else{
            System.out.println("Enter IDs one by one (Type 0 to finish):");
        }
        do {
            groupId = getIntInput(scanner);
            if (groupId != 0) {
                groupIds.add(groupId);
            }
        } while (groupId != 0);

        return groupIds;
    }

}

