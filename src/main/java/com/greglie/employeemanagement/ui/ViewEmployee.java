package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import com.greglie.employeemanagement.App;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel tableModel;
    JButton updateButton;
    Choice cemployeeId;
    JButton search, print, update, back;
    Connection conn = null;
    Statement stmt = null;
    private final App appController;

    public ViewEmployee(App controller) {
        this.appController = controller;
        setContentPane(new ImagePanel("view.jpg"));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        update = new JButton("Update");
        update.setBounds(570, 20, 100, 20);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                if (selectedRow != -1 && selectedColumn != -1) {
                    Object empIdObject = tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1);
                    String empId;
                    if (empIdObject instanceof Integer) {
                        empId = String.valueOf(empIdObject);
                    } else {
                        empId = (String) empIdObject;
                    }
                    String columnName = tableModel.getColumnName(selectedColumn);
                    try {
                        appController.showUpdateEmployeeScreen(empId); // Corrected
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewEmployee.this, "Please select a cell to update.");
                }
            }
        });
        add(update);

        JLabel lblSearch = new JLabel("Search Employee ID:");
        lblSearch.setBounds(20, 20, 150, 20);
        add(lblSearch);

        cemployeeId = new Choice();
        cemployeeId.setBounds(180, 20, 150, 20);
        add(cemployeeId);

        // Populate Employee IDs
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT empId FROM employee");

            while (rs.next()) {
                cemployeeId.add(rs.getString("empId"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        search = new JButton("Search");
        search.setBounds(350, 20, 100, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(460, 20, 100, 20);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(570, 20, 100, 20);
        update.addActionListener(new ActionListener() {
    @Override
   public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == search) {
        searchEmployee();
    } else if (ae.getSource() == print) {
        try {
            table.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == back) {
        setVisible(false);
        appController.showHomeScreen();
    }
}
});
        add(update);

        back = new JButton("Back");
        back.setBounds(680, 20, 100, 20);
        back.addActionListener(this);
        add(back);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 850, 500);
        add(scrollPane);

        loadEmployeeData(); 

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == search) {
        searchEmployee();
    } else if (ae.getSource() == print) {
        try {
            table.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == back) {
        setVisible(false);
        appController.showHomeScreen();
    }
}

    private void loadEmployeeData() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

            table.setModel(DbUtils.resultSetToTableModel(rs));
            tableModel = (DefaultTableModel) table.getModel(); 

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            String query = "SELECT * FROM employee WHERE empId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cemployeeId.getSelectedItem());

            rs = pstmt.executeQuery();

            if (rs != null) {
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                JOptionPane.showMessageDialog(this, "No employee found!", "Search Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new ViewEmployee(new App()));
    }
}