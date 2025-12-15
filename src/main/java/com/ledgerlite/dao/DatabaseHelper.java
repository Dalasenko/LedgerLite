package com.ledgerlite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // Αφαιρέσαμε το 'final' για να μπορούμε να το αλλάζουμε στα τεστ
    private static String url = "jdbc:sqlite:ledgerlite.db";

    // Μέθοδος για να αλλάζουμε βάση κατά τη διάρκεια των τεστ
    public static void setDatabase(String dbName) {
        url = "jdbc:sqlite:" + dbName;
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static void createNewDatabase() {
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
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }
}