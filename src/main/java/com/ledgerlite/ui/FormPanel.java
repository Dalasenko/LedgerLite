package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.util.StyleUtils;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormPanel extends JPanel {
    private JTextField titleField;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private JButton saveButton;
    private ExpenseDAO dao;
    private Runnable onSaveCallback;

    public FormPanel(ExpenseDAO dao, Runnable onSaveCallback) {
        this.dao = dao;
        this.onSaveCallback = onSaveCallback;

        // 1. Setup Layout
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the edge
        GridBagConstraints gbc = new GridBagConstraints();

        // 2. Common Layout Settings
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch width-wise
        gbc.insets = new Insets(0, 0, 10, 0); // Bottom margin for each item
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // 3. Components

        // Title Label & Field
        addLabel("Expense Title:", 0, gbc);
        titleField = new JTextField();
        titleField.putClientProperty("JTextField.placeholderText", "e.g. Starbucks");
        gbc.gridy = 1;
        add(titleField, gbc);

        // Amount Label & Field
        addLabel("Amount ($):", 2, gbc);
        amountField = new JTextField();
        amountField.putClientProperty("JTextField.placeholderText", "0.00");
        gbc.gridy = 3;
        add(amountField, gbc);

        // Category Label & Box
        addLabel("Category:", 4, gbc);
        categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Utilities", "Entertainment", "Health", "Other"});
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 25, 0); // Extra space before button
        add(categoryBox, gbc);

        // Save Button
        saveButton = new JButton("Add Transaction");
        saveButton.setFont(StyleUtils.FONT_BUTTON);
        saveButton.setBackground(StyleUtils.COLOR_ACCENT);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> save());

        gbc.gridy = 6;
        gbc.ipady = 10; // Make button slightly taller (internal padding)
        add(saveButton, gbc);

        // PUSH EVERYTHING UP (Spacer)
        gbc.gridy = 7;
        gbc.weighty = 1.0; // Take up all remaining vertical space
        gbc.fill = GridBagConstraints.BOTH;
        add(new JLabel(), gbc);
    }

    private void addLabel(String text, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(StyleUtils.FONT_LABEL);
        gbc.gridy = y;
        add(label, gbc);
    }

    private void save() {
        try {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title.");
                return;
            }
            double amount = Double.parseDouble(amountField.getText());
            String category = (String) categoryBox.getSelectedItem();

            dao.addExpense(title, amount, category, LocalDate.now());

            titleField.setText("");
            amountField.setText("");
            onSaveCallback.run(); // Refresh the rest of the app

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Amount. Please enter a number.");
        }
    }
}