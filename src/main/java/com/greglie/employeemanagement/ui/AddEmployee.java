package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployee extends JFrame implements ActionListener {

    private final App appController; 
    private JTextField tflname, tffname, tfaddress, tfphone, tfhuduma, tfemail, tfsalary, tfdesignation,tfempId;
    private JDateChooser dcdob;
    private JComboBox<String> cbeducation;
    private JButton add, back;
   ;

    public AddEmployee(App controller) {
        this.appController = controller;
        
        setContentPane(new ImagePanel("add_employee.jpg")); // Use add_employee.jpg
        setLayout(null);
        
        setTitle("Add Employee");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        
        //First Name
         JLabel lbfname = new JLabel("First Name:");
        lbfname.setBounds(50, 100, 100, 30);
        add(lbfname);
        
        tffname = new JTextField();
        tffname.setBounds(200, 100, 150, 30);
        add(tffname);
        
        //Last Name
        JLabel lblname = new JLabel("Last Name:");
        lblname.setBounds(50, 140, 100, 30);
        add(lblname);
        
        tflname = new JTextField();
        tflname.setBounds(200, 140, 150, 30);
        add(tflname);
        
        //Date of Birth
        JLabel lbldob = new JLabel("Date of Birth:");
        lbldob.setBounds(50, 180, 100, 30); 
        add(lbldob);

        dcdob = new JDateChooser(); 
        dcdob.setBounds(200, 180, 150, 30); 
        add(dcdob);
        
        //Salary
         JLabel lbsalary = new JLabel("Salary:");
        lbsalary.setBounds(50, 220, 150, 30);
        add(lbsalary);
        
        tfsalary = new JTextField();
        tfsalary.setBounds(200, 220, 150, 30);
        add(tfsalary);
        
        
        //Address
         JLabel lbaddress = new JLabel("Address:");
        lbaddress.setBounds(50, 260, 150, 30);
        add(lbaddress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(200, 260, 150, 30);
        add(tfaddress);
        //Phone
         JLabel lbphone = new JLabel("Phone:");
        lbphone.setBounds(50, 300, 150, 30);
        add(lbphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(200, 300, 150, 30);
        add(tfphone);
        
        //Email
         JLabel lbemail = new JLabel("Email:");
        lbemail.setBounds(50, 340, 100, 30);
        add(lbemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(200, 340, 150, 30);
        add(tfemail);

        //Hudumma
         JLabel lbhuduma = new JLabel("Huduma:");
        lbhuduma.setBounds(50, 380, 100, 30);
        add(lbhuduma);
        
        tfhuduma = new JTextField();
        tfhuduma.setBounds(200, 380, 150, 30);
        add(tfhuduma);
        
        //Education
        JLabel lbleducation = new JLabel("Education:");
        lbleducation.setBounds(50, 420, 100, 30); 
        add(lbleducation);

        String[] educationOptions = {" ","Primary", "Secondary", "Diploma", "Bachelor's", "Master's", "PhD"}; // Example education levels
        cbeducation = new JComboBox<>(educationOptions); 
        cbeducation.setBounds(200, 420, 150, 30); 
        add(cbeducation);
        
        //Designation
        JLabel lbdesignation = new JLabel("Designation:");
        lbdesignation.setBounds(50, 460, 100, 30);
        add(lbdesignation);
        
        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 460, 150, 30);
        add(tfdesignation);
        
        // Employee ID
        JLabel lblId = new JLabel("Employee ID:");
        lblId.setBounds(50, 500, 100, 30);
        add(lblId);

        tfempId = new JTextField();
        tfempId.setBounds(200, 500, 150, 30);
        add(tfempId);
        
        
        // Add Button
        add = new JButton("Add Employee");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        // Back Button
        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
         Connection dbConnection = null; 
            PreparedStatement pstmt = null;
            
            try {
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
                String query = "insert into employee values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                pstmt = dbConnection.prepareStatement(query);
              
            // Get data from text fields and date chooser
            String fname = tffname.getText();
            String lname = tflname.getText();
            java.util.Date dob = dcdob.getDate();
            String salary = tfsalary.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String huduma = tfhuduma.getText();
            String education = (String) cbeducation.getSelectedItem();
            String designation = tfdesignation.getText();
            String empId = tfempId.getText();
            
            // Set parameters for the PreparedStatement
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setDate(3, new java.sql.Date(dob.getTime()));
            pstmt.setString(4, salary);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, huduma);
            pstmt.setString(9, education);
            pstmt.setString(10, designation);
            pstmt.setString(11, empId);


           pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee added successfully!");
                setVisible(false);
                appController.showHomeScreen();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding employee: " + e.getMessage());
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (dbConnection != null) dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            setVisible(false);
            appController.showHomeScreen();
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new AddEmployee(new App()));
    }
}
