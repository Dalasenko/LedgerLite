package com.ledgerlite;

import com.formdev.flatlaf.FlatDarkLaf;
import com.ledgerlite.dao.DatabaseHelper;
import com.ledgerlite.ui.MainFrame;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // 1. Initialize the Database (Creates table if missing)
        DatabaseHelper.initDatabase();

        // 2. Setup the Dark Theme (FlatLaf)
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            // Optional: Make the window borders look native
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (Exception ex) {
            System.err.println("Failed to initialize Theme. Using default.");
        }

        // 3. Launch the Application safely
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}