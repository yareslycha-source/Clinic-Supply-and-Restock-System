package org.example.model;

public class SupplyItem {
    private int id;
    private String itemName;
    private String category;
    private int quantity;
    private double price;

    public SupplyItem(String itemName, String category, int quantity, double price) {
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public SupplyItem(int id, String itemName, String category, int quantity, double price) {
        this.id = id;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public String getItemName() { return itemName; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}