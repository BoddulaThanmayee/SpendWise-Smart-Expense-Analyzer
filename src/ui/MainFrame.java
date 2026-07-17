package ui;

import javax.swing.*;
import java.awt.*;

import dao.ExpenseDAO;
import model.Expense;
public class MainFrame extends JFrame {

    ExpenseDAO dao = new ExpenseDAO();

    public MainFrame() {

        setTitle("SpendWise – Smart Expense Analyzer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 5, 5));

        JButton salaryBtn = new JButton("Set Salary");
        JButton addBtn = new JButton("Add Expense");
        JButton viewBtn = new JButton("View Expenses");
        JButton analysisBtn = new JButton("Show Analysis");
        JButton reportBtn = new JButton("Monthly Report");
        JButton graphBtn = new JButton("Show Graph");
        JButton dailyBtn = new JButton("Daily Suggestion");
        JButton exitBtn = new JButton("Exit");

        add(salaryBtn);
        add(addBtn);
        add(viewBtn);
        add(analysisBtn);
        add(reportBtn);
        add(graphBtn);
        add(dailyBtn);
        add(exitBtn);

        // 🔥 Salary
        salaryBtn.addActionListener(e -> new SalaryFrame().setVisible(true));

        // 🔥 ADD EXPENSE (UPDATED)
        addBtn.addActionListener(e -> {
            try {
                String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:");
                if (amountStr == null) return;

                double amount = Double.parseDouble(amountStr);

                // 🔥 DATE INPUT (for now simple)
             // 🔥 DATE PICKER (Dropdown)

                String[] days = new String[31];
                for (int i = 1; i <= 31; i++) {
                    days[i - 1] = String.valueOf(i);
                }

                String[] months = {
                    "01","02","03","04","05","06",
                    "07","08","09","10","11","12"
                };

                String[] years = {"2024", "2025", "2026"};

                JComboBox<String> dayBox = new JComboBox<>(days);
                JComboBox<String> monthBox = new JComboBox<>(months);
                JComboBox<String> yearBox = new JComboBox<>(years);

                JPanel datePanel = new JPanel();
                datePanel.add(new JLabel("Day:"));
                datePanel.add(dayBox);
                datePanel.add(new JLabel("Month:"));
                datePanel.add(monthBox);
                datePanel.add(new JLabel("Year:"));
                datePanel.add(yearBox);

                int dateResult = JOptionPane.showConfirmDialog(
                        this,
                        datePanel,
                        "Select Date",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (dateResult != JOptionPane.OK_OPTION) return;

                String day = (String) dayBox.getSelectedItem();
                String month = (String) monthBox.getSelectedItem();
                String year = (String) yearBox.getSelectedItem();

                String date = year + "-" + month + "-" + day;
                if (date == null) return;

                // 🔥 CATEGORY UI
                JRadioButton food = new JRadioButton("Food");
                JRadioButton travel = new JRadioButton("Travel");
                JRadioButton bills = new JRadioButton("Bills");
                JRadioButton shopping = new JRadioButton("Shopping");
                JRadioButton others = new JRadioButton("Others");

                JTextField otherField = new JTextField();
                otherField.setEnabled(false);

                // Enable only for "Others"
                others.addActionListener(ev -> otherField.setEnabled(true));
                food.addActionListener(ev -> otherField.setEnabled(false));
                travel.addActionListener(ev -> otherField.setEnabled(false));
                bills.addActionListener(ev -> otherField.setEnabled(false));
                shopping.addActionListener(ev -> otherField.setEnabled(false));

                ButtonGroup group = new ButtonGroup();
                group.add(food);
                group.add(travel);
                group.add(bills);
                group.add(shopping);
                group.add(others);

                JPanel panel = new JPanel(new GridLayout(6, 1));
                panel.add(food);
                panel.add(travel);
                panel.add(bills);
                panel.add(shopping);
                panel.add(others);
                panel.add(otherField);

                int result = JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Select Category",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (result != JOptionPane.OK_OPTION) return;

                String category = "";

                if (food.isSelected()) category = "Food";
                else if (travel.isSelected()) category = "Travel";
                else if (bills.isSelected()) category = "Bills";
                else if (shopping.isSelected()) category = "Shopping";
                else if (others.isSelected()) category = otherField.getText();

                if (category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select category!");
                    return;
                }

                dao.addExpense(new Expense(amount, category, date));

                JOptionPane.showMessageDialog(this, "Expense Added!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // 🔥 VIEW
        viewBtn.addActionListener(e -> new ViewExpensesFrame().setVisible(true));

        // 🔥 ANALYSIS
        analysisBtn.addActionListener(e -> {

            double total = dao.getTotalExpense();
            String categoryData = dao.getCategoryWiseExpense();
            String highest = dao.getHighestSpendingCategory();

            String message =
                    "📊 ANALYSIS\n\n" +
                    "Total Expense: " + total + "\n\n" +
                    "Category-wise:\n" + categoryData + "\n" +
                    "Highest Spending: " + highest;

            JOptionPane.showMessageDialog(this, message);
        });

        // 🔥 REPORT
        reportBtn.addActionListener(e -> new MonthlyReportFrame().setVisible(true));

        // 🔥 GRAPH
        graphBtn.addActionListener(e -> new GraphFrame().setVisible(true));

        // 🔥 DAILY
        dailyBtn.addActionListener(e -> new DailySuggestionFrame().setVisible(true));

        // 🔥 EXIT
        exitBtn.addActionListener(e -> System.exit(0));

        // 🔔 Reminder (your existing feature)
        startReminder();
    }

    // 🔔 TIME-BASED REMINDER
    private void startReminder() {
        new Thread(() -> {
            while (true) {
                try {
                    java.time.LocalTime now = java.time.LocalTime.now();

                    if (now.getHour() == 20 && now.getMinute() == 0) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this,
                                    "🔔 Don't forget to enter today's expenses!");
                        });

                        Thread.sleep(60000);
                    }

                    Thread.sleep(60000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}