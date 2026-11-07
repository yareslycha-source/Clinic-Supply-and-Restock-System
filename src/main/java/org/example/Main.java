package org.example;

<<<<<<< HEAD
import org.example.ui.AdminMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== CLINIC SUPPLY & RESTOCK SYSTEM ===");
            System.out.println("1. Supply Inventory Management / Admin Menu");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            // Input validation wrapper to prevent crashing if user types letters
            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("INVALID INPUT: Please enter a valid number (1 or 0).");
                scanner.next(); // Clear invalid token
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Selected option: 1");
                    // Call your Admin Menu class and method here
                    try {
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.displayMenu(); // Change 'displayMenu()' if your method name in AdminMenu.java is different
                    } catch (Exception e) {
                        System.out.println("Error opening Admin Menu: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please select 1 or 0.");
            }
        }
        scanner.close();
=======
import org.example.ui.MenuUi;

public class Main {
    public static void main(String[] args) {
        MenuUi menuUi = new MenuUi();
        menuUi.start();
>>>>>>> 22f0f489ecbc58252be95d9653cfafbdda6b0698
    }
}