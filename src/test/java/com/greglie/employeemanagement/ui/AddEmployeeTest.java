/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.greglie.employeemanagement.ui;

import com.greglie.employeemanagement.App;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

/**
 *
 * @author elieb
 */
public class AddEmployeeTest {

    public AddEmployeeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Skip all GUI tests in a headless (CI/server) environment
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Skipping GUI test: no display available");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of actionPerformed method, of class AddEmployee.
     * Verifies that firing an ActionEvent on the form does not throw an exception.
     */
    @Test
    public void testActionPerformed() {
        App controller = Mockito.mock(App.class);
        AddEmployee instance = new AddEmployee(controller);

        ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "test");

        assertDoesNotThrow(() -> instance.actionPerformed(ae));

        instance.dispose();
    }
}