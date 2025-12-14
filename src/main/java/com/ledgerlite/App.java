package com.ledgerlite;

import com.formdev.flatlaf.FlatDarkLaf;
import com.ledgerlite.dao.DatabaseHelper;
import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.ui.MainFrame;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatDarkLaf()); } catch (Exception e) {}

        DatabaseHelper.createNewDatabase(); // Check DB
        new ExpenseDAO().deleteOldExpenses(); // Cleanup old days

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}