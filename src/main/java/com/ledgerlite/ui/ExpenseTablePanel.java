package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.model.Expense;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpenseTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ExpenseDAO dao;

    public ExpenseTablePanel(ExpenseDAO dao) {
        this.dao = dao;
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Title", "Amount", "Category", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // Context Menu for Deletion
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete Selected");
        deleteItem.addActionListener(e -> deleteSelected());
        popupMenu.add(deleteItem);
        table.setComponentPopupMenu(popupMenu);

        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        List<Expense> expenses = dao.getAllExpenses();
        for (Expense e : expenses) {
            model.addRow(e.toRow());
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            dao.deleteExpense(id);
            refresh();
        }
    }
}