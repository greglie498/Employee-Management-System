package Sem_project;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    Choice cEmpId;
    JButton delete, back;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    private final App appController;

    public RemoveEmployee(App controller) {
        this.appController = controller;
        setContentPane(new ImagePanel("remove.jpg"));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblEmpId = new JLabel("Employee ID:");
        lblEmpId.setBounds(50, 50, 100, 30);
        add(lblEmpId);

        cEmpId = new Choice();
        cEmpId.setBounds(150, 50, 150, 30);
        add(cEmpId);

        // Fetch employee IDs from database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            String query = "SELECT empId FROM employee";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        delete = new JButton("Delete");
        delete.setBounds(100, 300, 100, 30);
        delete.setBackground(Color.RED);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            deleteEmployee();
        } else {
            setVisible(false);
            appController.showHomeScreen();
        }
    }

    private void deleteEmployee() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            String query = "DELETE FROM employee WHERE empId=?"; 
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cEmpId.getSelectedItem());

            int deleted = pstmt.executeUpdate();
            if (deleted > 0) {
                JOptionPane.showMessageDialog(null, "Employee Information Deleted Successfully");
                cEmpId.remove(cEmpId.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(null, "Error: Employee Not Found");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new RemoveEmployee(new App()));
    }
}
