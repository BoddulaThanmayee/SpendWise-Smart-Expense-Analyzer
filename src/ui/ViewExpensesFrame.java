package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import util.DBConnection;

public class ViewExpensesFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewExpensesFrame() {
        setTitle("View Expenses");
        setSize(500, 300);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Amount");
        model.addColumn("Date");

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        try {
        	Connection conn = DBConnection.getConnection();
        	String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY date ASC";
        	PreparedStatement ps = conn.prepareStatement(sql);

        	ps.setInt(1, util.Session.userId); // 🔥 IMPORTANT

        	ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}