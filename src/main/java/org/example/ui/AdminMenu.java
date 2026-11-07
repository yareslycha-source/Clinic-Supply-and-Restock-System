package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final UserDao userDao;
    private final Scanner scanner;

    public AdminMenu(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n========================================");
            System.out.println("          ADMIN MANAGEMENT MENU         ");
            System.out.println("========================================");
            System.out.println("1. View All System Users");
            System.out.println("2. View Pending Registrations");
            System.out.println("3. Approve User Account");
            System.out.println("4. Logout / Return to Main");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllUsers();
                    break;
                case "2":
                    viewPendingUsers();
                    break;
                case "3":
                    approveUserAccount();
                    break;
                case "4":
                    System.out.println("Logging out from Admin Menu...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        List<User> users = userDao.getAllUsers();
        printUserTable(users, "ALL SYSTEM USERS");
    }

    private void viewPendingUsers() {
        List<User> pending = userDao.getPendingUsers();
        printUserTable(pending, "PENDING ACCOUNT REGISTRATIONS");
    }

    private void approveUserAccount() {
        List<User> pending = userDao.getPendingUsers();
        if (pending.isEmpty()) {
            System.out.println("\n[Info] There are currently no pending accounts to approve.");
            return;
        }

        printUserTable(pending, "PENDING USERS AWAITING APPROVAL");
        System.out.print("Enter Target User ID to approve (or enter 0 to cancel): ");

        String input = scanner.nextLine();
        int targetId;
        try {
            targetId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Action cancelled.");
            return;
        }

        if (targetId == 0) {
            System.out.println("Approval action cancelled.");
            return;
        }

        // Verify ID exists in the pending list
        boolean exists = pending.stream().anyMatch(u -> u.getId() == targetId);
        if (!exists) {
            System.out.println("Error: User ID " + targetId + " not found in the pending list.");
            return;
        }

        boolean success = userDao.approveUser(targetId);
        if (success) {
            System.out.println("\n[Success] User ID " + targetId + " status has been successfully updated to 'APPROVED'.");
        } else {
            System.out.println("\n[Error] Failed to update user status in the database.");
        }
    }

    // Structured table rendering helper method using standard string formatters
    private void printUserTable(List<User> users, String title) {
        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" " + title);
        System.out.println("-------------------------------------------------------------");
        if (users.isEmpty()) {
            System.out.println(" (No records found)");
            System.out.println("-------------------------------------------------------------");
            return;
        }

        // Format specifiers: %-5s (ID), %-15s (Username), %-18s (Role), %-12s (Status)
        String format = "| %-5s | %-15s | %-18s | %-12s |";
        System.out.println(String.format(format, "ID", "USERNAME", "ROLE", "STATUS"));
        System.out.println("|-------|-----------------|--------------------|--------------|");

        for (User u : users) {
            System.out.println(String.format(format,
                    u.getId(),
                    u.getUsername(),
                    u.getRole(),
                    u.getStatus()
            ));
        }
        System.out.println("-------------------------------------------------------------");
    }
}