package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;
import java.util.Scanner;

public class MenuUi {
    private final UserDao userDao;
    private final Scanner scanner;

    public MenuUi(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n========================================");
            System.out.println("   CLINIC SUPPLY AND RESTOCK SYSTEM     ");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDao.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid username or password.");
            return;
        }

        if ("PENDING".equalsIgnoreCase(user.getStatus())) {
            System.out.println("Your account is currently PENDING approval by an administrator.");
            return;
        }

        if ("REJECTED".equalsIgnoreCase(user.getStatus())) {
            System.out.println("Your account has been REJECTED.");
            return;
        }

        System.out.println("Login successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");
        routeUserMenu(user);
    }

    private void register() {
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine();

        if (userDao.getUserByUsername(username) != null) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Select Role:");
        System.out.println("1. CLINIC_STAFF");
        System.out.println("2. INVENTORY_MANAGER");
        System.out.println("3. ADMIN");
        System.out.print("Enter role choice (1-3): ");
        String roleChoice = scanner.nextLine();

        String role;
        switch (roleChoice) {
            case "1":
                role = "CLINIC_STAFF";
                break;
            case "2":
                role = "INVENTORY_MANAGER";
                break;
            case "3":
                role = "ADMIN";
                break;
            default:
                role = "CLINIC_STAFF";
                System.out.println("Invalid choice. Defaulted to CLINIC_STAFF.");
        }

        User newUser = new User(username, password, role, "PENDING");
        boolean success = userDao.registerUser(newUser);

        if (success) {
            System.out.println("Registration successful! Account registered with status: PENDING.");
        } else {
            System.out.println("Registration failed due to a database error.");
        }
    }

    private void routeUserMenu(User user) {
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            AdminMenu adminMenu = new AdminMenu(userDao, scanner);
            adminMenu.showMenu();
        } else {
            System.out.println("[Redirecting to " + user.getRole() + " dashboard... Press Enter to logout]");
            scanner.nextLine();
        }
    }
}