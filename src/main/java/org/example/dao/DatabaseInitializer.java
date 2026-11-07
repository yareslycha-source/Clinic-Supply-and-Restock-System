package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL, " +
                "status TEXT NOT NULL" +
                ");";

        String createSuppliesTable = "CREATE TABLE IF NOT EXISTS supplies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "reorder_level INTEGER NOT NULL" +
                ");";

        String createRestockRequestsTable = "CREATE TABLE IF NOT EXISTS restock_requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "supply_id INTEGER NOT NULL, " +
                "quantity_requested INTEGER NOT NULL, " +
                "status TEXT NOT NULL, " +
                "FOREIGN KEY (supply_id) REFERENCES supplies(id)" +
                ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUsersTable);
            stmt.execute(createSuppliesTable);
            stmt.execute(createRestockRequestsTable);

            String insertAdmin = "INSERT OR IGNORE INTO users (username, password, role, status) " +
                    "VALUES ('admin', 'admin123', 'ADMIN', 'APPROVED');";
            stmt.execute(insertAdmin);

            System.out.println("[DatabaseInitializer] Database and tables verified/created successfully.");
        } catch (SQLException e) {
            System.err.println("[DatabaseInitializer Error] " + e.getMessage());
        }
    }
}