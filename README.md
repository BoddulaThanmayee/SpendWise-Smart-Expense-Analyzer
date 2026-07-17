# 💰 SpendWise – Smart Expense Analyzer
A Java-based desktop application that helps users track, manage, and analyze their daily expenses. The application provides an easy-to-use interface for recording expenses, generating reports, viewing spending patterns, and managing personal finances efficiently.

---

## 📖 Project Overview
Managing daily expenses manually can be difficult and time-consuming. Many people forget where they spend their money, making budgeting and financial planning challenging.
**SpendWise** is a desktop application developed using **Java Swing**, **JDBC**, and **MySQL** that allows users to:

- Record daily expenses
- Categorize expenses
- View spending history
- Analyze spending patterns
- Generate reports
- Visualize expenses using graphs

---

## ✨ Features

- 👤 User Login System
- 💵 Monthly Salary Management
- ➕ Add Daily Expenses
- 📋 View Expense History
- 📊 Expense Analysis
- 📈 Daily Expense Graph
- 📅 Monthly Report
- 💡 Smart Spending Suggestions
- 👥 Multi-user Support
- 🔄 Database Connectivity using JDBC

---

## 🛠️ Technologies Used

### Programming Language
- Java

### User Interface
- Java Swing

### Database
- MySQL

### Database Connectivity
- JDBC (Java Database Connectivity)

### Chart Library
- JFreeChart

### IDE
- Eclipse IDE

### Version Control
- Git & GitHub

---

## 🏗️ Project Architecture

The project follows a layered architecture.

```
User
   │
   ▼
Java Swing (UI Layer)
   │
   ▼
DAO Layer (ExpenseDAO)
   │
   ▼
JDBC
   │
   ▼
MySQL Database
```

### UI Layer
Handles user interaction through Java Swing forms.

Examples:
- LoginFrame
- MainFrame
- GraphFrame

### DAO Layer
Handles all database operations.

Examples:
- Add Expense
- View Expense
- Analysis
- Reports

### JDBC Layer
Acts as a bridge between Java and MySQL.

Uses:
- DriverManager
- Connection
- PreparedStatement
- ResultSet

### Database Layer
Stores user information and expense records securely.

---

## 📦 Java Packages Used

### java.sql
Used for database connectivity.

Classes used:

- DriverManager
- Connection
- PreparedStatement
- ResultSet
- SQLException

### javax.swing
Used to build the graphical user interface.

### java.awt
Used for layouts and UI components.

### java.util
Used for utility classes such as Date and collections.

### org.jfree.chart
Used for displaying expense graphs.

---

## 💾 Database

### Database Name

```
expense_db
```

### Main Tables

### users

Stores login information.

Fields:

- id
- username
- password

### expenses

Stores expense records.

Fields:

- expense_id
- user_id
- amount
- category
- date
- description

---

## 🔄 Working Flow

1. User logs into the application.
2. Monthly salary is entered.
3. User adds daily expenses.
4. Expense details are stored in MySQL using JDBC.
5. User can view all expenses.
6. Analysis is generated using SQL queries.
7. Graphs visualize daily spending.
8. Reports help users understand spending habits.

---

## 📊 JDBC Workflow

```
Java Application
        │
        ▼
DriverManager
        │
        ▼
Connection
        │
        ▼
PreparedStatement
        │
        ▼
MySQL Database
        │
        ▼
ResultSet
        │
        ▼
Java Application
```

---

## 🔐 Security

The project uses **PreparedStatement** instead of Statement.

Benefits:

- Prevents SQL Injection
- Supports parameterized queries
- Improves performance
- Makes SQL queries more secure

---

## 🚀 Future Enhancements

- Mobile Application
- Cloud Database Integration
- AI-based Expense Prediction
- Email Notifications
- Export Reports to PDF
- Dark Mode
- Budget Goal Tracking

---

## 📸 Screenshots

Add screenshots here after uploading them.

Example:

- Login Page
- Dashboard
- Add Expense
- Expense List
- Analysis
- Graph
- Monthly Report

---

## ▶️ How to Run the Project

1. Clone the repository.

```
git clone https://github.com/YOUR_USERNAME/SpendWise-Smart-Expense-Analyzer.git
```

2. Open the project in Eclipse.

3. Import the MySQL database.

4. Add MySQL Connector/J library.

5. Configure database username and password in `DBConnection.java`.

6. Run the project.

---
## 📜 License

This project was developed for academic and educational purposes.

---

⭐ If you like this project, consider giving it a star on GitHub!
