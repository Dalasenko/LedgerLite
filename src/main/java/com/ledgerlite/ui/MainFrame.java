package com.ledgerlite.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private DashboardPanel dashboard;
    private ExpenseTablePanel table;
    private FormPanel form;

    public MainFrame() {
        setTitle("LedgerLite");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        dashboard = new DashboardPanel();
        table = new ExpenseTablePanel();

        // Όταν η φόρμα προσθέτει κάτι -> Κάνε refresh το Dashboard και τον Πίνακα
        form = new FormPanel(() -> {
            dashboard.refresh();
            table.refresh();
        });

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dashboard, table);
        split.setDividerLocation(250); // Δώσε ύψος στο Dashboard

        add(form, BorderLayout.WEST);
        add(split, BorderLayout.CENTER);

        // Initial load
        dashboard.refresh();
        table.refresh();
    }
}