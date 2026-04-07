package com.greglie.employeemanagement.approach4;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 4: Parameterized Testing
 * Same test logic runs across many data sets automatically.
 * Quality: Reliability, Correctness
 */
@DisplayName("Approach 4 - Parameterized Testing")
public class Approach4_ParameterizedTest {

    @ParameterizedTest(name = "TC4.1 [{index}] name={0} salary={1}")
    @CsvSource({
        "Alice, 50000.0",
        "Bob, 75000.0",
        "Carol, 90000.0",
        "David, 120000.0",
        "Eve, 35000.0",
        "Frank, 65000.0",
        "Grace, 80000.0",
        "Hank, 200000.0"
    })
    @DisplayName("TC4.1 - Constructor stores name and salary correctly")
    void tc1_nameAndSalary(String name, double salary) {
        Employee e = new Employee(1, name, "Role", salary);
        assertEquals(name, e.getName());
        assertEquals(salary, e.getSalary(), 0.001);
    }

    @ParameterizedTest(name = "TC4.2 [{index}] salary={0}")
    @ValueSource(doubles = {0.0, 1.0, 999.99, 50000.0, 1_000_000.0})
    @DisplayName("TC4.2 - setSalary accepts valid double values")
    void tc2_validSalaries(double salary) {
        Employee e = new Employee(1, "Test", "Role", 0);
        e.setSalary(salary);
        assertEquals(salary, e.getSalary(), 0.0001);
    }

    @ParameterizedTest(name = "TC4.3 [{index}] id={0}")
    @ValueSource(ints = {1, 10, 100, 999, Integer.MAX_VALUE})
    @DisplayName("TC4.3 - setId accepts various positive integers")
    void tc3_validIds(int id) {
        Employee e = new Employee(0, "Test", "Role", 0);
        e.setId(id);
        assertEquals(id, e.getId());
    }

    @ParameterizedTest(name = "TC4.4 [{index}] position={0}")
    @ValueSource(strings = {
        "Engineer","Manager","Analyst",
        "Director","Intern","Consultant","Architect","CEO"
    })
    @DisplayName("TC4.4 - setPosition accepts all standard job titles")
    void tc4_validPositions(String position) {
        Employee e = new Employee(1, "Test", position, 50000);
        assertEquals(position, e.getPosition());
    }

    @ParameterizedTest(name = "TC4.5 [{index}] name={0}")
    @NullAndEmptySource
    @DisplayName("TC4.5 - setName accepts null and empty without throwing")
    void tc5_nullAndEmptyName(String name) {
        Employee e = new Employee(1, "Test", "Role", 0);
        assertDoesNotThrow(() -> e.setName(name));
    }

    @ParameterizedTest(name = "TC4.6 [{index}] id={0} name={1} pos={2} salary={3}")
    @CsvSource({
        "1,Alice,Engineer,70000",
        "2,Bob,Manager,90000",
        "3,Carol,Intern,25000",
        "4,David,Director,150000",
        "5,Eve,Analyst,65000",
        "6,Frank,Consultant,80000",
        "7,Grace,Architect,110000",
        "8,Hank,CEO,200000"
    })
    @DisplayName("TC4.6 - Full 4-field constructor test across 8 data rows")
    void tc6_fullConstructor(int id, String name, String pos, double salary) {
        Employee e = new Employee(id, name, pos, salary);
        assertAll(
            () -> assertEquals(id,     e.getId()),
            () -> assertEquals(name,   e.getName()),
            () -> assertEquals(pos,    e.getPosition()),
            () -> assertEquals(salary, e.getSalary(), 0.001)
        );
    }

    static Stream<Employee> employeeStream() {
        return Stream.of(
            new Employee(1,"Alice","Eng",70000),
            new Employee(2,"Bob","Mgr",90000),
            new Employee(3,"Carol","Int",25000),
            new Employee(4,"David","Dir",150000),
            new Employee(5,"Eve","Analyst",65000)
        );
    }

    @ParameterizedTest(name = "TC4.7 [{index}] employee={0}")
    @MethodSource("employeeStream")
    @DisplayName("TC4.7 - MethodSource: Employee objects all valid")
    void tc7_methodSource(Employee e) {
        assertNotNull(e);
        assertFalse(e.getName().isEmpty());
        assertTrue(e.getSalary() >= 0);
    }
}