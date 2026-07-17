package ui;

import javax.swing.*;
import java.sql.*;

import util.DBConnection;
import util.Session;

public class MonthlyReportFrame extends JFrame {

    JTextArea area;

    public MonthlyReportFrame() {
        setTitle("Monthly Report");
        setSize(400, 400);

        area = new JTextArea();
        add(new JScrollPane(area));

        generateReport();
    }

    private void generateReport() {
        try {
            Connection conn = DBConnection.getConnection();

            // Salary
            String salarySql = "SELECT amount FROM salary ORDER BY id DESC LIMIT 1";
            PreparedStatement ps1 = conn.prepareStatement(salarySql);
            ResultSet rs1 = ps1.executeQuery();

            double salary = 0;
            if (rs1.next()) {
                salary = rs1.getDouble("amount");
            }

            // Total expense (USER ONLY)
            String totalSql = "SELECT SUM(amount) AS total FROM expenses WHERE MONTH(date)=MONTH(CURDATE()) AND user_id=?";
            PreparedStatement ps2 = conn.prepareStatement(totalSql);
            ps2.setInt(1, Session.userId);
            ResultSet rs2 = ps2.executeQuery();

            double total = 0;
            if (rs2.next()) {
                total = rs2.getDouble("total");
            }

            double percent = (salary == 0) ? 0 : (total / salary) * 100;

            // High spending days (USER ONLY)
            String highSql = "SELECT date, SUM(amount) AS total FROM expenses WHERE user_id=? GROUP BY date ORDER BY total DESC LIMIT 2";
            PreparedStatement ps3 = conn.prepareStatement(highSql);
            ps3.setInt(1, Session.userId);
            ResultSet rs3 = ps3.executeQuery();

            String highDays = "";
            while (rs3.next()) {
                highDays += rs3.getString("date") + " → " + rs3.getDouble("total") + "\n";
            }

            String suggestion;
            if (percent > 80) {
                suggestion = "⚠ You are overspending!";
            } else if (percent > 50) {
                suggestion = "⚠ Be careful with spending.";
            } else {
                suggestion = "✅ Good financial management!";
            }

            area.setText(
                    "---- Monthly Report ----\n\n" +
                    "Salary: " + salary + "\n" +
                    "Total Expense: " + total + "\n" +
                    "Used: " + percent + "%\n\n" +
                    "High Spending Days:\n" + highDays + "\n" +
                    "Suggestion:\n" + suggestion
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}