/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Management;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;


import java.util.Scanner;

import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author islam
 */
public class Tools {
    public static void disableMongoLogging() { 
    /*
    * Static method: Disable annoying mongo log messages
    * This method require add some code to POM file
    * https://stackoverflow.com/questions/30137564/how-to-disable-mongodb-java-driver-logging 
    */
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR); 
    }
    
    
    public static void waitTimeUser(){
        /*
        * Static method: Makes the user wait to not collapse programm
        */
        try {
            // Retraso de 5 segundos (5000 milisegundos)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }
    
    // Auxiliar function to allow save an empty value
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
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
    
    public static void pressAnyKeyToContinue() {
        System.out.println(""); // Just a separator
        System.out.println("Press Enter key to continue...");
        try {
                System.in.read();
        } catch (Exception ex) {
                System.out.println("Oooops! Something was wrong!");
                ex.printStackTrace(System.out);
        }
    }
    
    public static String convertDateToString() {
        // get actual date
        LocalDate currentDate = LocalDate.now();

        // Format date to this format (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }
    
    public static int getIntInput(Scanner scanner) {
    int value = 0;
    boolean validInput = false;
    do {
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter an integer.");
                continue;
            }
            value = Integer.parseInt(input);
            validInput = true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer.");
        }
    } while (!validInput);

    return value;
    }
}
