package org.example;

import org.example.dao.DatabaseConnection;
import org.example.ui.AdminMenu;

public class Main {
    public static void main(String[] args) {
        // Initialize database tables
        DatabaseConnection.initializeDatabase();

        // Launch the application menu
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.start();
    }
}