package com.greglie.employeemanagement.approach11;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 11: Negative Testing
 * Confirms the system handles invalid/unexpected input without crashing.
 * Quality: Robustness, Fault Tolerance
 */
@DisplayName("Approach 11 - Negative Testing")
public class Approach11_NegativeTest {

    @Test @DisplayName("TC11.1 - Null name does not throw exception")
    void tc1() { assertDoesNotThrow(() -> new Employee(1,null,"Role",0)); }

    @Test @DisplayName("TC11.2 - Empty name does not throw exception")
    void tc2() { assertDoesNotThrow(() -> new Employee(1,"","Role",0)); }

    @Test @DisplayName("TC11.3 - Null position does not throw exception")
    void tc3() { assertDoesNotThrow(() -> new Employee(1,"Test",null,0)); }

    @Test @DisplayName("TC11.4 - Negative salary does not throw exception")
    void tc4() { assertDoesNotThrow(() -> new Employee(1,"Test","Role",-9999)); }

    @Test @DisplayName("TC11.5 - Negative ID does not throw exception")
    void tc5() { assertDoesNotThrow(() -> new Employee(-1,"Test","Role",0)); }

    @Test @DisplayName("TC11.6 - Zero ID does not throw exception")
    void tc6() { assertDoesNotThrow(() -> new Employee(0,"Test","Role",0)); }

    @Test @DisplayName("TC11.7 - setName(null) does not throw exception")
    void tc7() {
        Employee e = new Employee(1,"Test","Role",0);
        assertDoesNotThrow(() -> e.setName(null));
    }

    @Test @DisplayName("TC11.8 - setPosition(null) does not throw exception")
    void tc8() {
        Employee e = new Employee(1,"Test","Role",0);
        assertDoesNotThrow(() -> e.setPosition(null));
    }

    @Test @DisplayName("TC11.9 - Very large salary stored without exception")
    void tc9() {
        assertDoesNotThrow(() ->
            new Employee(1,"Test","Role", Double.MAX_VALUE));
    }

    @Test @DisplayName("TC11.10 - Integer.MIN_VALUE ID stored without exception")
    void tc10() {
        assertDoesNotThrow(() ->
            new Employee(Integer.MIN_VALUE,"Test","Role",0));
    }
}