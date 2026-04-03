/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import com.greglie.employeemanagement.controller.AppController;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

/**
 *
 * @author elieb
 */
public class HomeTest {
    
    public HomeTest() {
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

    /**
     * Test of actionPerformed method, of class Home.
     */
    @Test
    public void testActionPerformed() {
       App controller = Mockito.mock(App.class);
       Home instance =new Home(controller);
       
       ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "test");
       
       assertDoesNotThrow(() -> instance.actionPerformed(ae));
        
    }

    /**
     * Test of main method, of class Home.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Home.main(args);
        
    }
    
}
