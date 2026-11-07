package org.example.ui;

import org.example.dao.SupplyDao;
import org.example.model.SupplyItem;

import java.util.List;
import java.util.Scanner;

public class MenuUi {
    private final SupplyDao supplyDao;
    private final Scanner scanner;

    public MenuUi() {
        this.supplyDao = new SupplyDao();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = 0;

        do {
            System.out.println("\n=== CLINIC SUPPLY & RESTOCK SYSTEM ===");
            System.out.println("[1] Add Record");
            System.out.println("[2] View Records");
            System.out.println("[3] Exit System");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addRecordFlow();
                        break;
                    case 2:
                        viewRecordsFlow();
                        break;
                    case 3:
                        System.out.println("Exiting system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid token
            }

        } while (choice != 3);

        scanner.close();
    }

    private void addRecordFlow() {
        System.out.println("\n--- Add New Supply Record ---");
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Category (e.g., Medicine, PPE, Equipment): ");
        String category = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Unit Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        SupplyItem newItem = new SupplyItem(itemName, category, quantity, price);
        supplyDao.addRecord(newItem);
    }

    private void viewRecordsFlow() {
        List<SupplyItem> items = supplyDao.getAllRecords();

        System.out.println("\n--- Clinic Supply Records ---");
        if (items.isEmpty()) {
            System.out.println("No records found in the database.");
            return;
        }

        System.out.printf("+-----+----------------------+------------------+----------+------------+\n");
        System.out.printf("| ID  | Item Name            | Category         | Quantity | Price      |\n");
        System.out.printf("+-----+----------------------+------------------+----------+------------+\n");

        for (SupplyItem item : items) {
            System.out.printf("| %-3d | %-20s | %-16s | %-8d | ₱%-9.2f |\n",
                    item.getId(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getPrice());
        }
        System.out.printf("+-----+----------------------+------------------+----------+------------+\n");
    }
}