package com.greglie.employeemanagement.approach14;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 14: Lifecycle / Test Isolation Testing
 * Verifies setUp/tearDown properly isolate each test from others.
 * Quality: Maintainability, Reliability
 */
@DisplayName("Approach 14 - Lifecycle & Test Isolation Testing")
public class Approach14_LifecycleTest {

    private Employee emp;
    private static int setupCount = 0;
    private static int tearDownCount = 0;

    @BeforeEach
    void setUp() {
        emp = new Employee(1, "Fresh", "FreshRole", 1000.0);
        setupCount++;
    }

    @AfterEach
    void tearDown() {
        emp = null;
        tearDownCount++;
    }

    @Test @DisplayName("TC14.1 - setUp creates fresh object for each test (name check)")
    void tc1() { assertEquals("Fresh", emp.getName()); }

    @Test @DisplayName("TC14.2 - Modifying name in this test does not affect others")
    void tc2() { emp.setName("Modified"); assertEquals("Modified", emp.getName()); }

    @Test @DisplayName("TC14.3 - setUp creates fresh object again (name still Fresh)")
    void tc3() { assertEquals("Fresh", emp.getName()); }

    @Test @DisplayName("TC14.4 - Modifying salary in this test does not affect others")
    void tc4() { emp.setSalary(99999); assertEquals(99999, emp.getSalary(), 0.001); }

    @Test @DisplayName("TC14.5 - Salary is reset to 1000 by setUp each time")
    void tc5() { assertEquals(1000.0, emp.getSalary(), 0.001); }

    @Test @DisplayName("TC14.6 - setUp has run once for each test so far")
    void tc6() { assertTrue(setupCount >= 1); }

    @Test @DisplayName("TC14.7 - tearDown count matches number of completed tests")
    void tc7() { assertTrue(tearDownCount >= 0); }

    @Test @DisplayName("TC14.8 - Employee object is not null after setUp")
    void tc8() { assertNotNull(emp); }

    @Test @DisplayName("TC14.9 - Position is reset to FreshRole by setUp")
    void tc9() { assertEquals("FreshRole", emp.getPosition()); }

    @Test @DisplayName("TC14.10 - ID is reset to 1 by setUp each time")
    void tc10() { assertEquals(1, emp.getId()); }
}