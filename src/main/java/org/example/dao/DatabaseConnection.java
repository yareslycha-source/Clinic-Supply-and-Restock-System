package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:clinic_supply.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        // Automatically create tables if needed or keep blank for startup hooks
        UserDao userDao = new UserDao();
        userDao.createTable();
    }
}