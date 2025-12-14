package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.model.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ExpenseTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ExpenseDAO expenseDAO;

    public ExpenseTablePanel() {
        expenseDAO = new ExpenseDAO();
        setLayout(new BorderLayout());

        // Στήλες: # (σειρά), Title, Amount, Category, Date, DB_ID (Κρυφό)
        String[] cols = {"#", "Title", "Amount", "Category", "Date", "DB_ID"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        // Center text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Hide DB_ID column (Index 5)
        table.removeColumn(table.getColumnModel().getColumn(5));

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Delete Button
        JButton delBtn = new JButton("Delete Selected");
        delBtn.addActionListener(e -> deleteSelected());
        add(delBtn, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        List<Expense> list = expenseDAO.getAllExpenses();
        int rowNum = 1;
        for (Expense e : list) {
            model.addRow(new Object[]{
                    rowNum++, // 1, 2, 3...
                    e.getTitle(),
                    String.format("%.2f", e.getAmount()),
                    e.getCategory(),
                    e.getDate(),
                    e.getId() // Real ID (Hidden)
            });
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int dbId = (int) model.getValueAt(row, 5); // Get hidden ID
            expenseDAO.deleteExpense(dbId);
            refresh();
        }
    }
}