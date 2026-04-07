package com.greglie.employeemanagement.approach3;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 3: Boundary Value Analysis (BVA)
 * Tests fields at minimum, maximum and just-outside boundaries.
 * Quality: Reliability, Robustness
 */
@DisplayName("Approach 3 - Boundary Value Analysis")
public class Approach3_BoundaryTest {

    private Employee emp;

    @BeforeEach
    void setUp() { emp = new Employee(1, "Test", "Role", 1000.0); }

    @Test
    @DisplayName("TC3.1 - Salary at exactly zero (lower boundary)")
    void tc1_salaryAtZero() {
        emp.setSalary(0.0);
        assertEquals(0.0, emp.getSalary(), 0.001);
    }

    @Test
    @DisplayName("TC3.2 - Salary just above zero (0.01)")
    void tc2_salaryJustAboveZero() {
        emp.setSalary(0.01);
        assertEquals(0.01, emp.getSalary(), 0.0001);
    }

    @Test
    @DisplayName("TC3.3 - Salary just below zero (-0.01)")
    void tc3_salaryJustBelowZero() {
        emp.setSalary(-0.01);
        assertEquals(-0.01, emp.getSalary(), 0.0001);
    }

    @Test
    @DisplayName("TC3.4 - Salary at Double.MAX_VALUE (upper boundary)")
    void tc4_salaryMaxDouble() {
        emp.setSalary(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, emp.getSalary(), 1.0);
    }

    @Test
    @DisplayName("TC3.5 - ID at 1 (minimum valid ID)")
    void tc5_idAtOne() {
        emp.setId(1);
        assertEquals(1, emp.getId());
    }

    @Test
    @DisplayName("TC3.6 - ID at 0 (zero boundary)")
    void tc6_idAtZero() {
        emp.setId(0);
        assertEquals(0, emp.getId());
    }

    @Test
    @DisplayName("TC3.7 - ID at Integer.MAX_VALUE (upper boundary)")
    void tc7_idAtIntMax() {
        emp.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, emp.getId());
    }

    @Test
    @DisplayName("TC3.8 - ID at -1 (below lower boundary)")
    void tc8_idNegative() {
        emp.setId(-1);
        assertEquals(-1, emp.getId());
    }

    @Test
    @DisplayName("TC3.9 - Name with exactly 1 character")
    void tc9_nameOneChar() {
        emp.setName("A");
        assertEquals("A", emp.getName());
    }

    @Test
    @DisplayName("TC3.10 - Name with 255 characters (typical DB max)")
    void tc10_name255Chars() {
        String name = "A".repeat(255);
        emp.setName(name);
        assertEquals(255, emp.getName().length());
    }
}