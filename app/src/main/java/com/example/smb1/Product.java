package com.example.smb1;

public class Product {
    private long id;
    private String name;
    private int amount;
    private double price;
    private boolean complete;

    public Product(long id, String name, int amount, double price, boolean complete) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.complete = complete;
    }

    public Product(String name, int amount, double price, boolean complete) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.complete = complete;
    }

    public Product(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + '(' + amount + " x " + price + ')';
    }
}
