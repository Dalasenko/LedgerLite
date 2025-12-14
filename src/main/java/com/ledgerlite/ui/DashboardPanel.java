package com.ledgerlite.ui;

import com.ledgerlite.dao.ExpenseDAO;
import com.ledgerlite.util.StyleUtils;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DashboardPanel extends JPanel {
    private ExpenseDAO dao;

    public DashboardPanel(ExpenseDAO dao) {
        this.dao = dao;
        setLayout(new BorderLayout());
        setBackground(StyleUtils.COLOR_BG_PANEL); // Dark background
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Expense Analytics");
        title.setFont(StyleUtils.FONT_HEADER);
        title.setForeground(StyleUtils.COLOR_TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Map<String, Double> data = dao.getCategoryTotals();

        // If empty, draw a nice placeholder
        if (data.isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.setFont(StyleUtils.FONT_LABEL);
            g2.drawString("Add your first expense to see analytics.", getWidth() / 2 - 100, getHeight() / 2);
            return;
        }

        double maxVal = data.values().stream().max(Double::compare).orElse(1.0);
        int barWidth = 50;
        int gap = 30;
        int startX = 40;
        int baseline = getHeight() - 40;
        int maxBarHeight = getHeight() - 100;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int height = (int) ((entry.getValue() / maxVal) * maxBarHeight);

            // Draw Bar with Gradient
            GradientPaint gp = new GradientPaint(startX, baseline, StyleUtils.COLOR_ACCENT.darker(), startX, baseline - height, StyleUtils.COLOR_ACCENT);
            g2.setPaint(gp);
            g2.fillRoundRect(startX, baseline - height, barWidth, height, 15, 15);

            // Draw Value on top
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            String price = String.format("$%.0f", entry.getValue());
            g2.drawString(price, startX + (barWidth - g2.getFontMetrics().stringWidth(price)) / 2, baseline - height - 5);

            // Draw Category Label below
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            String cat = entry.getKey();
            // Truncate if too long
            if(cat.length() > 8) cat = cat.substring(0, 8) + "..";
            g2.drawString(cat, startX + (barWidth - g2.getFontMetrics().stringWidth(cat)) / 2, baseline + 20);

            startX += (barWidth + gap);
        }
    }

    public void refresh() {
        repaint();
    }
}