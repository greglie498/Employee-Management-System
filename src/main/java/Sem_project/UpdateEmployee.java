package Sem_project;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfNewValue;
    JLabel lblNewValue;
    JButton updateButton, back;
    Connection conn = null;
    PreparedStatement pstmt = null;
    private final App appController;
    private String empId;
    private String columnName;

    public UpdateEmployee(String empId, String columnName, App controller) throws SQLException {
        this.empId = empId;
        this.columnName = columnName;
        this.appController = controller != null ? controller : new App();
        setContentPane(new ImagePanel("second.jpg"));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        lblNewValue = new JLabel("New Value for " + columnName + ":");
        lblNewValue.setBounds(50, 100, 200, 30);
        add(lblNewValue);

        tfNewValue = new JTextField();
        tfNewValue.setBounds(250, 100, 200, 30);
        add(tfNewValue);

        updateButton = new JButton("Update");
        updateButton.setBounds(200, 150, 150, 40);
        updateButton.addActionListener(this);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.WHITE);
        add(updateButton);

        back = new JButton("Back");
        back.setBounds(400, 150, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(600, 300);
        setLocation(300, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            updateColumn();
        } else {
            setVisible(false);
            appController.showHomeScreen();
        }
    }

    private void updateColumn() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/employee_db", "root", "");
            String query = "UPDATE employee SET " + columnName + " = ? WHERE empId = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, tfNewValue.getText());
            pstmt.setString(2, empId);

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "Column updated successfully");
                setVisible(false);
                appController.showHomeScreen();
            } else {
                JOptionPane.showMessageDialog(null, "No records updated. Check Employee ID.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}