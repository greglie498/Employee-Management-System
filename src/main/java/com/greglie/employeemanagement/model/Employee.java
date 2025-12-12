/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greglie.employeemanagement.model;

public class Employee{
    private int id;
    private String name;
    private String position;
    private double salary;
    
    // Constructor
    public Employee(int id, String name, String position, double salary){
        this.id=  id;
        this.name= name;
        this.position= position;
        this.salary= salary;
    }
    
    // Getters and Setters
    public int getId(){
        return id;
    }
    public void  setId(){
        this.id= id;
    }
    
    
    public String getName(){
        return name;
    }
    public void setName(){
        this.name= name;
    }
    
    public String getPosition(){
        return position;
    }
    public void setPosition(){
        this.position= position;
    }
    
    public double getSalary(){
        return salary;
    }
    public void setSalary(){
        this.salary= salary;
    }


}
