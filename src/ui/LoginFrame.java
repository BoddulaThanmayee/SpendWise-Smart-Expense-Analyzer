package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import util.DBConnection;
import util.Session;

public class LoginFrame extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public LoginFrame() {

        setTitle("SpendWise Login");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // 🔹 TITLE
        JLabel title = new JLabel("SpendWise Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        // 🔹 FORM PANEL (aligned properly)
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        formPanel.add(new JLabel("Username:"));
        userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        panel.add(formPanel, BorderLayout.CENTER);

        // 🔹 BUTTON PANEL
        JButton loginBtn = new JButton("Login");
        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);

        // 🔥 LOGIN LOGIC
        loginBtn.addActionListener(e -> login());
    }

    private void login() {
        try {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Session.userId = rs.getInt("id");

                String dbPass = rs.getString("password");

                if (dbPass.equals(pass)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");

                    new MainFrame().setVisible(true);
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Wrong Password!");
                }

            } else {
                // New user auto-create
                String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement ps2 = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

                ps2.setString(1, user);
                ps2.setString(2, pass);
                ps2.executeUpdate();

                ResultSet keys = ps2.getGeneratedKeys();
                if (keys.next()) {
                    Session.userId = keys.getInt(1);
                }

                JOptionPane.showMessageDialog(this, "New User Created!");

                new MainFrame().setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}