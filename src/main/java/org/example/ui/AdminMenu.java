package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner;
    private UserDao userDao;
    private User currentAdmin;

    public AdminMenu(User currentAdmin) {
        this.scanner = new Scanner(System.in);
        this.userDao = new UserDao();
        this.currentAdmin = currentAdmin;
    }

    public AdminMenu() {
        this.scanner = new Scanner(System.in);
        this.userDao = new UserDao();
        this.currentAdmin = new User(1, "AdminUser", "ADMIN", "APPROVED");
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== ADMIN MENU - USER MANAGEMENT ===");
            System.out.println("1. View All Users");
            System.out.println("2. Update User Role and Status");
            System.out.println("0. Back to Main Menu");

            int choice = readIntInput("Select an option: ");

            switch (choice) {
                case 1:
                    displayUserTable();
                    break;
                case 2:
                    updateUserWorkflow();
                    break;
                case 0:
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please choose 1, 2, or 0.");
            }
        }
    }

    public void displayUserTable() {
        System.out.println("\n---------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-10s | %-10s%n", "ID", "Username", "Role", "Status");
        System.out.println("---------------------------------------------------------");

        List<User> users = userDao.getAllUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("No users found in the database.");
            return;
        }

        for (User u : users) {
            System.out.printf("%-5d | %-15s | %-10s | %-10s%n",
                    u.getId(), u.getUsername(), u.getRole(), u.getStatus());
        }
        System.out.println("---------------------------------------------------------");
    }

    public void updateUserWorkflow() {
        displayUserTable();

        System.out.println("\n[Note: Enter 0 at any step to cancel]");
        int targetUserId = readIntInput("Enter the Target User ID to update: ");

        if (targetUserId == 0) {
            System.out.println("Update operation cancelled.");
            return;
        }

        if (targetUserId == currentAdmin.getId()) {
            System.out.println("WARNING: Modifying the currently active admin account is restricted.");
            return;
        }

        String newRole = readValidRole();
        if (newRole.equalsIgnoreCase("CANCEL")) {
            System.out.println("Update operation cancelled.");
            return;
        }

        String newStatus = readValidStatus();
        if (newStatus.equalsIgnoreCase("CANCEL")) {
            System.out.println("Update operation cancelled.");
            return;
        }

        userDao.updateUserRoleAndStatus(targetUserId, newRole.toUpperCase(), newStatus.toUpperCase());
        System.out.println("SUCCESS: User ID " + targetUserId + " updated successfully!");
    }

    public int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                System.out.println("INVALID INPUT: Please enter a valid number.");
                scanner.next();
            }
        }
    }

    public String readValidRole() {
        while (true) {
            System.out.print("Enter new role (STUDENT, TEACHER, ADMIN) [0 to cancel]: ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return "CANCEL";
            }

            String upper = input.toUpperCase();
            if (upper.equals("STUDENT") || upper.equals("TEACHER") || upper.equals("ADMIN")) {
                return upper;
            } else {
                System.out.println("INVALID ROLE: Only STUDENT, TEACHER, or ADMIN are accepted. Try again.");
            }
        }
    }

    public String readValidStatus() {
        while (true) {
            System.out.print("Enter new status (APPROVED, PENDING) [0 to cancel]: ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return "CANCEL";
            }

            String upper = input.toUpperCase();
            if (upper.equals("APPROVED") || upper.equals("PENDING")) {
                return upper;
            } else {
                System.out.println("INVALID STATUS: Only APPROVED or PENDING are accepted. Try again.");
            }
        }
    }
}