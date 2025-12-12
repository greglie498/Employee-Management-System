package com.greglie.employeemanagement.util;

import java.sql.*;

public class Conn {

   Connection c = null; // Initialize to null
    Statement s;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // SECURITY RISK!

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public Statement getStatement() {
        return s;
    }
  
    public static void main(String[] args) throws SQLException {
        Conn conn = new Conn();
        Connection c = conn.getConnection();
        Statement s = conn.getStatement();
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM employee"; // Using the consistent table name
            rs = s.executeQuery(query);

            while (rs.next()) {
                System.out.println("Employee ID: " + rs.getInt("empId")); // Assuming empId is the ID column
                System.out.println("Employee Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}