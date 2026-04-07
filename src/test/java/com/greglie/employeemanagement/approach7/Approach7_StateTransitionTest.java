package com.greglie.employeemanagement.approach7;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 7: State Transition Testing
 * Verifies object state changes correctly after sequential mutations.
 * Quality: Reliability, Correctness
 */
@DisplayName("Approach 7 - State Transition Testing")
public class Approach7_StateTransitionTest {

    private Employee emp;

    @BeforeEach
    void setUp() {
        emp = new Employee(1, "Initial", "InitialRole", 1000.0);
    }

    @Test
    @DisplayName("TC7.1 - Name transitions from initial to updated value")
    void tc1_nameTransition() {
        assertEquals("Initial", emp.getName());
        emp.setName("Updated");
        assertEquals("Updated", emp.getName());
    }

    @Test
    @DisplayName("TC7.2 - Salary transitions: low → high → zero")
    void tc2_salaryMultipleTransitions() {
        emp.setSalary(50000.0); assertEquals(50000.0, emp.getSalary(), 0.001);
        emp.setSalary(100000.0); assertEquals(100000.0, emp.getSalary(), 0.001);
        emp.setSalary(0.0); assertEquals(0.0, emp.getSalary(), 0.001);
    }

    @Test
    @DisplayName("TC7.3 - ID transitions: 1 → 2 → 3")
    void tc3_idIncrements() {
        emp.setId(1); assertEquals(1, emp.getId());
        emp.setId(2); assertEquals(2, emp.getId());
        emp.setId(3); assertEquals(3, emp.getId());
    }

    @Test
    @DisplayName("TC7.4 - Position career path: Intern → Junior → Senior → Lead")
    void tc4_positionCareerPath() {
        emp.setPosition("Intern");      assertEquals("Intern",      emp.getPosition());
        emp.setPosition("Junior Dev");  assertEquals("Junior Dev",  emp.getPosition());
        emp.setPosition("Senior Dev");  assertEquals("Senior Dev",  emp.getPosition());
        emp.setPosition("Tech Lead");   assertEquals("Tech Lead",   emp.getPosition());
    }

    @Test
    @DisplayName("TC7.5 - Name changed then reverted to original")
    void tc5_nameRevert() {
        String original = emp.getName();
        emp.setName("Temp");
        emp.setName(original);
        assertEquals(original, emp.getName());
    }

    @Test
    @DisplayName("TC7.6 - Salary raised then cut (promotion then demotion)")
    void tc6_salaryRaiseThenCut() {
        emp.setSalary(80000.0);
        emp.setSalary(60000.0);
        assertEquals(60000.0, emp.getSalary(), 0.001);
    }

    @Test
    @DisplayName("TC7.7 - All 4 fields change independently and correctly")
    void tc7_allFieldsChange() {
        emp.setId(99);
        emp.setName("NewName");
        emp.setPosition("NewRole");
        emp.setSalary(999999.0);
        assertAll(
            () -> assertEquals(99,        emp.getId()),
            () -> assertEquals("NewName", emp.getName()),
            () -> assertEquals("NewRole", emp.getPosition()),
            () -> assertEquals(999999.0,  emp.getSalary(), 0.001)
        );
    }

    @Test
    @DisplayName("TC7.8 - ID transition: negative → zero → positive")
    void tc8_idFullRange() {
        emp.setId(-5);  assertEquals(-5,  emp.getId());
        emp.setId(0);   assertEquals(0,   emp.getId());
        emp.setId(100); assertEquals(100, emp.getId());
    }

    @Test
    @DisplayName("TC7.9 - Name set 10 times, final value is retained")
    void tc9_nameManyTimes() {
        for (int i = 0; i < 9; i++) emp.setName("Name" + i);
        emp.setName("FinalName");
        assertEquals("FinalName", emp.getName());
    }

    @Test
    @DisplayName("TC7.10 - Salary decimal precision preserved across transitions")
    void tc10_salaryDecimalPrecision() {
        emp.setSalary(1234.56);
        emp.setSalary(7890.12);
        assertEquals(7890.12, emp.getSalary(), 0.001);
    }
}