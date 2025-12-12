/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greglie.employeemanagement.service;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import com.greglie.employeemanagement.util.Conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;
   
    // Constructor initialies the DAO with a DB connection
    public EmployeeService() throws SQLException{
        Conn c = new Conn();
        Connection connection = c.getConnection();
        this.employeeDAO = new EmployeeDAO(connection);
    }
    
    //Add a new employee
    public boolean addEmployee(Employee employee){
        try{
            employeeDAO.addEmployee(employee);
            System.out.println("Employee added successfully");
            return true;
        } catch (SQLException e){
         System.err.println("Failed to add employee: " + e.getMessage());
         return false;
        }
    }
    
    //update existing employee
    public boolean updateEmployee(Employee employee){
        try{
            employeeDAO.updateEmployee(employee);
            System.out.println("Employee updated successfully");
            return true;
        } catch (SQLException e){
         System.err.println("Failed to update employee: " + e.getMessage());
         return false;
        }
    }
    
    //Remove employee by ID
    public boolean removeEmployee(int id){
        try{
            employeeDAO.deleteEmployee(id);
            System.out.println("Employee removed successfully");
            return true;
        } catch (SQLException e){
         System.err.println("Failed to remove employee: " + e.getMessage());
         return false;
        }
    }
    
    // Get single employee by ID
    public Employee getEmployeeById(int id){
        try{
           return employeeDAO.getEmployeeById(id);
        } catch (SQLException e){
         System.err.println("Failed to retrieve employee: " + e.getMessage());
         return null;
        }
    }
    
    //Get all employees
    public List<Employee> getAllEmployees(){
        try{
           return employeeDAO.getAllEmployees();
        } catch (SQLException e){
         System.err.println("Failed to retrieve employee: " + e.getMessage());
         return null;
        }
    }
    
}
