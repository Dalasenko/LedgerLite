package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.model.Expense;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardPanel extends JPanel {
    private ExpenseDAO expenseDAO;
    private JLabel totalLabel;
    private JPanel chartPanel;

    public DashboardPanel() {
        expenseDAO = new ExpenseDAO();
        setLayout(new BorderLayout());
        setBackground(new Color(40, 44, 52));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Total Amount Label
        totalLabel = new JLabel("Total: 0.00€");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        totalLabel.setForeground(new Color(100, 255, 218)); // Έντονο χρώμα (Cyan)
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(totalLabel, BorderLayout.NORTH);

        // Custom Panel για ζωγραφική
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChart(g);
            }
        };
        chartPanel.setOpaque(false);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void refresh() {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        totalLabel.setText(String.format("Total Expenses: %.2f€", total));
        chartPanel.repaint(); // Ξαναζωγραφίζει το γράφημα
    }

    private void drawChart(Graphics g) {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        if (expenses.isEmpty()) return;

        Map<String, Double> data = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 20;
        int barWidth = 80;
        int height = chartPanel.getHeight() - 40;
        int baseY = chartPanel.getHeight() - 20;

        double maxVal = data.values().stream().mapToDouble(d -> d).max().orElse(1.0);

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() / maxVal) * (height - 50));

            // Μπάρα
            g2.setColor(new Color(65, 105, 225)); // Royal Blue
            g2.fillRoundRect(x, baseY - barHeight, barWidth, barHeight, 10, 10);

            // Κείμενο Κατηγορίας
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.drawString(entry.getKey(), x + 5, baseY + 15);

            // Κείμενο Ποσού (πάνω από τη μπάρα)
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString(String.format("%.0f€", entry.getValue()), x + 5, baseY - barHeight - 5);

            x += barWidth + 40;
        }
    }
}