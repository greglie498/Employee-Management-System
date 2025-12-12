package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import com.greglie.employeemanagement.util.Conn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField tfpassword;
    private App appController; 

    public Login(App controller) { 
        this.appController = controller;
        setContentPane(new ImagePanel("front.jpg"));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 30);
        lblusername.setForeground(Color.WHITE);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 30);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 30);
         lblpassword.setForeground(Color.WHITE);
        add(lblpassword);

        tfpassword = new JPasswordField(); 
        tfpassword.setBounds(150, 70, 150, 30);
        add(tfpassword);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        setSize(600, 300);
        setLocation(450, 200);
        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent ae) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        String username = tfusername.getText();
        String password = new String(tfpassword.getPassword());

        Conn c = new Conn();
        conn = c.getConnection();
        String query = "SELECT * FROM log_in WHERE username = ? AND password = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        rs = pstmt.executeQuery();
        if (rs.next()) {
            setVisible(false);
            appController.loginSuccessful();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password");
            setVisible(false);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Login(new App()); 
        });
    }
}