package com.greglie.employeemanagement.approach1;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 1: Unit Testing - Employee Model (POJO)
 * Tests every getter, setter and constructor field.
 * Quality: Reliability, Correctness
 */
@DisplayName("Approach 1 - Unit Testing: Employee Model")
public class Approach1_UnitTest {

    private Employee emp;

    @BeforeEach
    void setUp() {
        emp = new Employee(1, "Alice", "Engineer", 50000.0);
    }

    @AfterEach
    void tearDown() { emp = null; }

    @Test
    @DisplayName("TC1.1 - Constructor sets ID correctly")
    void tc1_constructorSetsId() {
        assertEquals(1, emp.getId());
    }

    @Test
    @DisplayName("TC1.2 - Constructor sets Name correctly")
    void tc2_constructorSetsName() {
        assertEquals("Alice", emp.getName());
    }

    @Test
    @DisplayName("TC1.3 - Constructor sets Position correctly")
    void tc3_constructorSetsPosition() {
        assertEquals("Engineer", emp.getPosition());
    }

    @Test
    @DisplayName("TC1.4 - Constructor sets Salary correctly")
    void tc4_constructorSetsSalary() {
        assertEquals(50000.0, emp.getSalary(), 0.001);
    }

    @Test
    @DisplayName("TC1.5 - setId() updates the ID field")
    void tc5_setIdUpdates() {
        emp.setId(99);
        assertEquals(99, emp.getId());
    }

    @Test
    @DisplayName("TC1.6 - setName() updates the Name field")
    void tc6_setNameUpdates() {
        emp.setName("Bob");
        assertEquals("Bob", emp.getName());
    }

    @Test
    @DisplayName("TC1.7 - setPosition() updates the Position field")
    void tc7_setPositionUpdates() {
        emp.setPosition("Manager");
        assertEquals("Manager", emp.getPosition());
    }

    @Test
    @DisplayName("TC1.8 - setSalary() updates the Salary field")
    void tc8_setSalaryUpdates() {
        emp.setSalary(75000.0);
        assertEquals(75000.0, emp.getSalary(), 0.001);
    }

    @Test
    @DisplayName("TC1.9 - Employee object is not null after creation")
    void tc9_objectNotNull() {
        assertNotNull(emp);
    }

    @Test
    @DisplayName("TC1.10 - getName returns String type")
    void tc10_getNameReturnsString() {
        assertInstanceOf(String.class, emp.getName());
    }
}