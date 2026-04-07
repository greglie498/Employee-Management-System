package com.greglie.employeemanagement.approach13;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 13: Contract Testing
 * Verifies the public API contract — return types, not-null guarantees.
 * Quality: Usability, Portability
 */
@DisplayName("Approach 13 - Contract Testing")
public class Approach13_ContractTest {

    private Employee emp;
    @BeforeEach void setUp() { emp = new Employee(1,"Alice","Dev",60000); }

    @Test @DisplayName("TC13.1 - getId() never returns negative for a valid object")
    void tc1() { assertTrue(emp.getId() >= 0); }

    @Test @DisplayName("TC13.2 - getName() returns a non-null String by contract")
    void tc2() { assertNotNull(emp.getName()); }

    @Test @DisplayName("TC13.3 - getPosition() returns a non-null String by contract")
    void tc3() { assertNotNull(emp.getPosition()); }

    @Test @DisplayName("TC13.4 - getSalary() returns a double (not int)")
    void tc4() { assertInstanceOf(Double.class, emp.getSalary()); }

    @Test @DisplayName("TC13.5 - Employee object implements no-arg setter contract")
    void tc5() { assertDoesNotThrow(() -> emp.setId(10)); }

    @Test @DisplayName("TC13.6 - getName() after setName() returns exactly what was set")
    void tc6() { emp.setName("Bob"); assertEquals("Bob", emp.getName()); }

    @Test @DisplayName("TC13.7 - getSalary() after setSalary() returns exactly what was set")
    void tc7() { emp.setSalary(99999.0); assertEquals(99999.0, emp.getSalary(), 0.001); }

    @Test @DisplayName("TC13.8 - getPosition() after setPosition() returns exactly what was set")
    void tc8() { emp.setPosition("CTO"); assertEquals("CTO", emp.getPosition()); }

    @Test @DisplayName("TC13.9 - Constructor with all args produces non-null object")
    void tc9() { assertNotNull(new Employee(5,"X","Y",0)); }

    @Test @DisplayName("TC13.10 - getId() after setId() returns exactly what was set")
    void tc10() { emp.setId(42); assertEquals(42, emp.getId()); }
}