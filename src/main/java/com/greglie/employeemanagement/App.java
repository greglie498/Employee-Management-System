package com.greglie.employeemanagement;

import com.greglie.employeemanagement.ui.ViewEmployee;
import com.greglie.employeemanagement.ui.UpdateEmployee;
import com.greglie.employeemanagement.ui.Login;
import com.greglie.employeemanagement.ui.Splash;
import com.greglie.employeemanagement.ui.RemoveEmployee;
import com.greglie.employeemanagement.ui.Home;
import com.greglie.employeemanagement.ui.AddEmployee;
import com.greglie.employeemanagement.util.Conn;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class App {
    Conn connScreen;
    private Splash splashScreen;
    private Login loginScreen;
    private Home homeScreen;
    private ViewEmployee viewEmployeeScreen;
    private UpdateEmployee updateEmployeeScreen;
    private RemoveEmployee removeEmployeeScreen;
    private AddEmployee addEmployeeScreen;

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            try {
                if (app.initialize()) {
                    app.start();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to connect to the database. Application will exit.", "Database Error", JOptionPane.ERROR_MESSAGE); 
                    System.exit(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean initialize() throws SQLException {
        connScreen = new Conn();
        if (connScreen.getConnection() == null) {
            return false; 
        }
        return true; 
    }

    public void start() {
        splashScreen = new Splash(this);
    }

    public void showLoginScreen() {
        if (splashScreen != null) {
            splashScreen.dispose(); 
        }
        if (loginScreen == null) {
            loginScreen = new Login(this);
        }
        loginScreen.setVisible(true);
    }

    public void showHomeScreen() {
        closeCurrentScreen(loginScreen);
        if (homeScreen == null) {
            homeScreen = new Home(this);
        }
        homeScreen.setVisible(true);
    }

    public void showAddEmployeeScreen() {
        closeCurrentScreen(homeScreen); // Close home before opening AddEmployee
        if (addEmployeeScreen == null) {
            addEmployeeScreen = new AddEmployee(this);
        }
        addEmployeeScreen.setVisible(true);
    }

    public void showViewEmployeeScreen() {
        closeCurrentScreen(homeScreen);
        if (viewEmployeeScreen == null) {
            viewEmployeeScreen = new ViewEmployee(this);
        }
        viewEmployeeScreen.setVisible(true);
    }

    public void showUpdateEmployeeScreen(String empId) throws SQLException {
        System.out.println("showUpdateEmployeeScreen called. EmpId: "+ empId); //debugging line.
        closeCurrentScreen(viewEmployeeScreen);
        if (updateEmployeeScreen != null) {
            updateEmployeeScreen.dispose();
        }
        updateEmployeeScreen = new UpdateEmployee(empId,  this);
        updateEmployeeScreen.setVisible(true);
    }

    public void showViewEmployeeScreenForUpdate() {
        showViewEmployeeScreen(); // Use existing method
    }

    public void showRemoveEmployeeScreen() {
        closeCurrentScreen(homeScreen);
        if (removeEmployeeScreen == null) {
            removeEmployeeScreen = new RemoveEmployee(this);
        }
        removeEmployeeScreen.setVisible(true);
    }

    public void loginSuccessful() {
        showHomeScreen(); // Redirect to home screen
    }

    private void closeCurrentScreen(JFrame currentScreen) {
        if (currentScreen != null) {
            currentScreen.setVisible(false);
            currentScreen.dispose();
        }
    }
}
