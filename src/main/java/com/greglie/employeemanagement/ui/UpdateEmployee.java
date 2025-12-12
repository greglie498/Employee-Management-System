/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import com.greglie.employeemanagement.util.Conn;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener{

    // Fields for text input, now using class-level scope
    JTextField tfeducation, tffname, tfaddress, tfphone, tfemail, tfsalary, tfdesignation;
    
    // Labels that display read-only or employee data
    JLabel lblname, lbldob, lblhuduma, lblempId;
    
    // Buttons
    JButton updateDetails, back; 
    
    // Employee ID passed from ViewEmployee
    String empId;
    
    // Controller for navigation
    private final App appController;
    
    // Database resources - best practice is to manage them locally (try-with-resources)
    // but kept as fields for visibility into the connection pattern used in other files.
    private Connection conn = null; 

    // Main Constructor
    public UpdateEmployee(String empId, App controller) {
        this.empId = empId;
        this.appController = controller;
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Update Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        // --- Row 1: Name (Read-only) & Father's Name (Editable) ---
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 150, 150, 30);
        lblname.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblname);

        JLabel labelfname = new JLabel("Father's Name");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelfname);

        tffname = new JTextField();
        tffname.setBounds(600, 150, 150, 30);
        add(tffname);

        // --- Row 2: Date of Birth (Read-only) & Salary (Editable) ---
        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        lbldob = new JLabel();
        lbldob.setBounds(200, 200, 150, 30);
        lbldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(lbldob);

        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);
        
        // --- Row 3: Address (Editable) & Phone (Editable) ---
        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        // --- Row 4: Email (Editable) & Highest Education (Editable) ---
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        JLabel labeleducation = new JLabel("Highest Education"); // Fixed typo
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        tfeducation = new JTextField();
        tfeducation.setBounds(600, 300, 150, 30);
        add(tfeducation);
        
        // --- Row 5: Designation (Editable) & Huduma No. (Read-only) ---
        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        JLabel labelhuduma = new JLabel("Huduma Number");
        labelhuduma.setBounds(400, 350, 150, 30);
        labelhuduma.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelhuduma);

        lblhuduma = new JLabel();
        lblhuduma.setBounds(600, 350, 150, 30);
        lblhuduma.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblhuduma);

        // --- Row 6: Employee ID (Read-only) ---
        JLabel labelempId = new JLabel("Employee id");
        labelempId.setBounds(50, 400, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);

        lblempId = new JLabel();
        lblempId.setBounds(200, 400, 150, 30);
        lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblempId);

        // --- Fetch and Populate Data ---
        fetchEmployeeData();
        
        // --- Buttons ---
        updateDetails = new JButton("Update Details");
        updateDetails.setBounds(250, 550, 150, 40);
        updateDetails.addActionListener(this);
        updateDetails.setBackground(Color.BLACK);
        updateDetails.setForeground(Color.WHITE);
        add(updateDetails);

        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        // setVisible(true); // Don't set visible here; App class will manage visibility
    }
    
    // Secondary constructor for compatibility/testing (still relies on App)
    public UpdateEmployee(String empId) {
        this(empId, new App()); // Calls main constructor with a dummy App instance
    }
    
    // --- Helper method to fetch employee data ---
    private void fetchEmployeeData() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Conn c = new Conn();
            conn = c.getConnection();
            String query = "SELECT * FROM employee WHERE empId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, empId);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                lblname.setText(rs.getString("name"));
                tffname.setText(rs.getString("fname"));
                lbldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfsalary.setText(rs.getString("salary"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfeducation.setText(rs.getString("education"));
                lblhuduma.setText(rs.getString("huduma"));
                lblempId.setText(rs.getString("empId"));
                tfdesignation.setText(rs.getString("designation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee data: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // We keep conn open as other methods might need it, but best practice is to close it.
                // Since this is a temporary screen, we'll close it in actionPerformed finally.
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // --- Action Handler ---
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateDetails) {
            String fname = tffname.getText();
            String salary = tfsalary.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String education = tfeducation.getText();
            String designation = tfdesignation.getText();
            
            PreparedStatement pstmt = null;
            try {
                // Ensure connection is open for the update
                if (conn == null || conn.isClosed()) {
                    conn = new Conn().getConnection();
                }
                
                String query = "UPDATE employee SET fname = ?, salary = ?, address = ?, phone = ?, email = ?, education = ?, designation = ? WHERE empId = ?";
                pstmt = conn.prepareStatement(query);
                
                pstmt.setString(1, fname);
                pstmt.setString(2, salary);
                pstmt.setString(3, address);
                pstmt.setString(4, phone);
                pstmt.setString(5, email);
                pstmt.setString(6, education);
                pstmt.setString(7, designation);
                pstmt.setString(8, empId); // WHERE clause
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Details updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Update failed or Employee ID not found.");
                }
                
                setVisible(false);
                appController.showHomeScreen(); // Navigate back to Home
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error during update: " + e.getMessage());
            } finally {
                // Close resources after the operation
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Back button pressed
            setVisible(false);
            appController.showHomeScreen();
        }
    }

    public static void main(String[] args) {
        // For independent testing
        javax.swing.SwingUtilities.invokeLater(() -> new UpdateEmployee("123456", new App()));
    }
}

