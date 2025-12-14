package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.model.Expense;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormPanel extends JPanel {
    private JTextField titleField;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private ExpenseDAO expenseDAO;
    private Runnable onAddCallback;

    public FormPanel(Runnable onAddCallback) {
        this.onAddCallback = onAddCallback;
        this.expenseDAO = new ExpenseDAO();

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 0));
        setBackground(new Color(50, 54, 62));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Title
        addLabel("Title:", gbc, 0);
        titleField = new JTextField();
        gbc.gridy = 1;
        add(titleField, gbc);

        // Amount
        addLabel("Amount (€):", gbc, 2);
        amountField = new JTextField();
        gbc.gridy = 3;
        add(amountField, gbc);

        // Category
        addLabel("Category:", gbc, 4);
        categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Shopping", "Bills", "Fun"});
        gbc.gridy = 5;
        add(categoryBox, gbc);

        // Button
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(30, 136, 229));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addTransaction());
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 15, 10, 15);
        add(addBtn, gbc);

        // Spacer
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        add(new JLabel(), gbc);
    }

    private void addTransaction() {
        try {
            String title = titleField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String cat = (String) categoryBox.getSelectedItem();

            if (!title.isEmpty()) {
                expenseDAO.addExpense(new Expense(0, title, amount, cat, LocalDate.now()));
                titleField.setText("");
                amountField.setText("");
                if (onAddCallback != null) onAddCallback.run(); // TRIGGER REFRESH
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Amount");
        }
    }

    private void addLabel(String text, GridBagConstraints gbc, int y) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = y;
        add(l, gbc);
    }
}