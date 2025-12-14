package com.ledgerlite.model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    public Expense(int id, String title, double amount, String category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }

    public Object[] toRow() {
        return new Object[]{id, title, String.format("$%.2f", amount), category, date};
    }
}