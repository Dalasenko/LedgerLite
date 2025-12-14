package com.ledgerlite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:ledgerlite.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createNewDatabase() {
        // ΕΔΩ ΕΓΙΝΕ Η ΑΛΛΑΓΗ: Χρησιμοποιούμε 'title' αντί για 'description'
        String sql = "CREATE TABLE IF NOT EXISTS expenses (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " amount REAL NOT NULL,\n"
                + " category TEXT,\n"
                + " date TEXT NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database created with column 'title'.");
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }
}