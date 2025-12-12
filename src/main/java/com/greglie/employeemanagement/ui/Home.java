package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    private JButton viewButton = null;
    private JButton addButton = null;
    private JButton updateButton = null;
    private JButton removeButton = null;
    private final App appController;

    public Home(App controller) {
        this.appController = controller;
        setContentPane(new ImagePanel("home.jpg"));
        setupUI();
    }

    private void setupUI() {
        Color deepBlue = new Color(0, 0, 139);
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null); // Use null layout for manual positioning

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setForeground(deepBlue);
        titleLabel.setBounds(300, 20, 500, 50); // Adjust bounds as needed
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(titleLabel);

        // Button styling (same as before)
        Font btnFont = new Font("Arial", Font.BOLD, 14);
        Color btnColor = new Color(45, 140, 255);
        Dimension btnSize = new Dimension(180, 45);

        // Buttons
        addButton = createStyledButton("Add Employee", 650, 180, btnSize, btnFont, btnColor); 
        viewButton = createStyledButton("View Employees", 850, 180, btnSize, btnFont, btnColor); 
        updateButton = createStyledButton("Update Employee", 650, 240, btnSize, btnFont, btnColor); 
        removeButton = createStyledButton("Remove Employee", 850, 240, btnSize, btnFont, btnColor); 

        add(addButton);
        add(viewButton);
        add(updateButton);
        add(removeButton);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private JButton createStyledButton(String text, int x, int y, Dimension size, Font font, Color color) {
        JButton button = new JButton(text);
        button.setBounds(x, y, size.width, size.height);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton sourceButton = (JButton) ae.getSource();
        dispose(); // Close the current frame properly

        if (sourceButton == addButton) {
            appController.showAddEmployeeScreen();
        } else if (sourceButton == viewButton) {
            appController.showViewEmployeeScreen();
        } else if (sourceButton == updateButton) {
            appController.showViewEmployeeScreenForUpdate();
        } else if (sourceButton == removeButton) {
            appController.showRemoveEmployeeScreen();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home(new App()));
    }
}