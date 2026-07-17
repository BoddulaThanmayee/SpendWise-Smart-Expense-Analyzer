package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.*;

import util.DBConnection;
import util.Session;

public class GraphFrame extends JFrame {

    public GraphFrame() {
        setTitle("Daily Expense Graph");
        setSize(600, 400);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT date, SUM(amount) AS total \r\n"
            		+ "FROM expenses \r\n"
            		+ "WHERE user_id = ? \r\n"
            		+ "GROUP BY date \r\n"
            		+ "ORDER BY date ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Session.userId);   // 🔥 IMPORTANT

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                double total = rs.getDouble("total");

                dataset.addValue(total, "Expense", date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Daily Expenses",
                "Date",
                "Amount",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }
}