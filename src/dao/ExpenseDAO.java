package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Expense;
import util.DBConnection;
import util.Session;

public class ExpenseDAO {

    // ✅ ADD EXPENSE
    public void addExpense(Expense expense) {

        String sql = "INSERT INTO expenses (amount, category, date, user_id) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, expense.getAmount());
            ps.setString(2, expense.getCategory());
            ps.setString(3, expense.getDate());
            ps.setInt(4, Session.userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ TOTAL EXPENSE (RETURN VALUE)
    public double getTotalExpense() {
        double total = 0;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT SUM(amount) AS total FROM expenses WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // ✅ CATEGORY WISE (RETURN STRING)
    public String getCategoryWiseExpense() {

        String result = "";

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT category, SUM(amount) AS total FROM expenses WHERE user_id = ? GROUP BY category";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result += rs.getString("category") + " : " + rs.getDouble("total") + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ✅ HIGHEST CATEGORY (RETURN STRING)
    public String getHighestSpendingCategory() {

        String result = "";

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT category, SUM(amount) AS total FROM expenses WHERE user_id = ? GROUP BY category ORDER BY total DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString("category") + " (" + rs.getDouble("total") + ")";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ✅ TODAY EXPENSE
    public double getTodayExpense() {

        double total = 0;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT SUM(amount) AS total FROM expenses WHERE date = CURDATE() AND user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Session.userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}