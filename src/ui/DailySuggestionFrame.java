package ui;

import javax.swing.*;
import dao.ExpenseDAO;

public class DailySuggestionFrame extends JFrame {

    JTextArea area;
    ExpenseDAO dao = new ExpenseDAO();

    public DailySuggestionFrame() {
        setTitle("Daily Suggestion");
        setSize(300, 200);

        area = new JTextArea();
        add(area);

        showSuggestion();
    }

    private void showSuggestion() {
        double total = dao.getTodayExpense(); // ✅ already user-based

        String message;

        if (total > 1000) {
            message = "⚠ High spending today!\nTry to reduce expenses.";
        } else if (total > 500) {
            message = "⚠ Moderate spending.\nBe cautious.";
        } else {
            message = "✅ Good control on spending today!";
        }

        area.setText("Today's Expense: ₹" + total + "\n\n" + message);
    }
}