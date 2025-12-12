/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greglie.employeemanagement.dao;

import com.greglie.employeemanagement.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection conn;
    
    public EmployeeDAO(Connection conn)  {
        this.conn = conn;
        }
    
    public void addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1, emp.getName());
        stmt.setString(2, emp.getPosition());
        stmt.setDouble(3, emp.getSalary());
        stmt.executeUpdate();
    }
    
    public void updateEmployee (Employee emp) throws SQLException {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, emp.getName());
        stmt.setString(2, emp.getPosition());
        stmt.setDouble(3, emp.getSalary());
        stmt.setInt(4, emp.getId());
        stmt.executeUpdate();
    }
    
    public void deleteEmployee(int id) throws SQLException {
        String sql =  "DELETE FROM employees WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    
    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            return new Employee (rs.getInt("id"), rs.getString("name"),
            rs.getString("position"), rs.getDouble("salary"));
        }
        return null;
    }
    
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            list.add(new  Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("position"),
            rs.getDouble("salary")
            ));
        }
        return list;
    }
    
}
