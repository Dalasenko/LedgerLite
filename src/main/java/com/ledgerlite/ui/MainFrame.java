package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ExpenseDAO dao;
    private DashboardPanel dashboard;
    private ExpenseTablePanel tablePanel;
    private FormPanel formPanel;

    public MainFrame() {
        dao = new ExpenseDAO();
        // CHANGED HERE: Updated to 2026
        setTitle("LedgerLite - 2026 Edition");

        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        dashboard = new DashboardPanel(dao);
        tablePanel = new ExpenseTablePanel(dao);
        formPanel = new FormPanel(dao, this::refreshApp);

        // Preferred size fixes the "Squashed" look
        formPanel.setPreferredSize(new Dimension(300, getHeight()));

        // Layout - Split pane for Right side (Dashboard on top, Table on bottom)
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dashboard, tablePanel);
        rightSplit.setDividerLocation(300); // Give dashboard 300px height
        rightSplit.setResizeWeight(0.5);
        rightSplit.setBorder(null);

        // Main Layout
        add(formPanel, BorderLayout.WEST);
        add(rightSplit, BorderLayout.CENTER);
    }

    private void refreshApp() {
        dashboard.refresh();
        tablePanel.refresh();
    }
}