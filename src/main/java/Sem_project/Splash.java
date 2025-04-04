package Sem_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Splash extends JFrame implements ActionListener {

    private JLabel heading;
    private boolean headingVisible = true;
    private Timer timer;
    private final App appController; 
    
    
    public Splash(App controller) { 
        this.appController = controller;
        setContentPane(new ImagePanel("details.jpg"));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null); // Consider using a layout manager

        // Heading Label
        heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM", SwingConstants.CENTER);
        heading.setBounds(50, 30, 1050, 60);
        heading.setFont(new Font("Serif", Font.BOLD, 50));
        heading.setForeground(Color.RED);
        add(heading);

        // Click Button
        JButton clickhere = new JButton("CLICK HERE TO CONTINUE");
        clickhere.setBounds(375, 380, 300, 50);
        clickhere.setBackground(Color.BLACK);
        clickhere.setForeground(Color.WHITE);
        clickhere.setFont(new Font("Arial", Font.BOLD, 16));
        clickhere.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clickhere.addActionListener(this);
        add(clickhere);
      

        // Frame Configuration
        setSize(1170, 650);
        setLocation(200, 50);
        setVisible(true);

        // Blinking Effect
        timer = new Timer(500, e -> {
            headingVisible = !headingVisible;
            heading.setVisible(headingVisible);
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.stop(); 
        dispose();
        appController.showLoginScreen(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Splash(new App())); 
    }
}