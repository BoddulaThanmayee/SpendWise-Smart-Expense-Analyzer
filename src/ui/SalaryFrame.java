package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import util.DBConnection;

public class SalaryFrame extends JFrame {

    JTextField salaryField;

    public SalaryFrame() {
        setTitle("Set Monthly Salary");
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));

        JLabel label = new JLabel("Enter Salary:");
        salaryField = new JTextField();

        JButton saveBtn = new JButton("Save");

        add(label);
        add(salaryField);
        add(new JLabel());
        add(saveBtn);

        saveBtn.addActionListener(e -> saveSalary());
    }

    private void saveSalary() {
        try {
            double amount = Double.parseDouble(salaryField.getText());

            Connection conn = DBConnection.getConnection();

            // 🔥 CHECK IF SALARY ALREADY EXISTS THIS MONTH
            String checkSql = "SELECT * FROM salary WHERE MONTH(CURDATE()) = MONTH(CURDATE()) LIMIT 1";
            PreparedStatement ps1 = conn.prepareStatement(checkSql);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                        "Salary already set for this month!");
                return;
            }

            // 🔥 INSERT SALARY
            String insertSql = "INSERT INTO salary (amount, month) VALUES (?, MONTHNAME(CURDATE()))";
            PreparedStatement ps2 = conn.prepareStatement(insertSql);
            ps2.setDouble(1, amount);
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Salary Saved!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error!");
        }
        int today = java.time.LocalDate.now().getDayOfMonth();

        if (today != 1) {
            JOptionPane.showMessageDialog(this,
                "You can only set salary on the 1st day of the month!");
            return;
        }
    }
    
}