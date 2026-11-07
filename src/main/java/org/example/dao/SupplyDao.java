package org.example.dao;

import org.example.model.SupplyItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDao {

    public SupplyDao() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS clinic_supplies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item_name TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "price REAL NOT NULL)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void addRecord(SupplyItem item) {
        String sql = "INSERT INTO clinic_supplies (item_name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getCategory());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getPrice());

            pstmt.executeUpdate();
            System.out.println("\n[Success] Supply record added to database successfully!");
        } catch (SQLException e) {
            System.out.println("\n[Error] Failed to add record: " + e.getMessage());
        }
    }

    public List<SupplyItem> getAllRecords() {
        List<SupplyItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clinic_supplies";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new SupplyItem(
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("\n[Error] Failed to fetch records: " + e.getMessage());
        }
        return items;
    }
}