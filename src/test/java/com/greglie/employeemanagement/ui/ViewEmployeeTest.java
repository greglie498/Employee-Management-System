/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;

/**
 *
 * @author elieb
 */
public class ViewEmployeeTest {
    
    public ViewEmployeeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Disabled("Skipped: requires a display screen (HeadlessException)")
    @Test
    public void testActionPerformed() {
       App controller = Mockito.mock(App.class);
       ViewEmployee instance =new ViewEmployee(controller);
       
       ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "test");
       
       assertDoesNotThrow(() -> instance.actionPerformed(ae));
        
    }

    /**
     * Test of main method, of class ViewEmployee.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ViewEmployee.main(args);
        
    }
    
}
