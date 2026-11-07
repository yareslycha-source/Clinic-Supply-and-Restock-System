package org.example.ui;

import java.util.Scanner;

public class AdminMenu {

    // Default no-argument constructor
    public AdminMenu() {
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== CLINIC SUPPLY & RESTOCK SYSTEM ===");
        System.out.println("1. Supply Inventory Management");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");

        int choice = scanner.nextInt();
        System.out.println("Selected option: " + choice);
        scanner.close();
    }
}