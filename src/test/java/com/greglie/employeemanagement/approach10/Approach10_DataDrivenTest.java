package com.greglie.employeemanagement.approach10;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 10: Data-Driven Testing
 * External data sets drive the same test logic repeatedly.
 * Quality: Reliability, Coverage
 */
@DisplayName("Approach 10 - Data-Driven Testing")
public class Approach10_DataDrivenTest {

    static Stream<Arguments> employeeData() {
        return Stream.of(
            Arguments.of(1,"Alice","Engineer",70000.0, true),
            Arguments.of(2,"Bob","Manager",90000.0,    true),
            Arguments.of(3,"Carol","Intern",25000.0,   true),
            Arguments.of(4,"David","Director",150000.0,true),
            Arguments.of(5,"Eve","Analyst",65000.0,    true),
            Arguments.of(6,"Frank","Consultant",80000.0,true),
            Arguments.of(7,"Grace","Architect",110000.0,true),
            Arguments.of(8,"Hank","CEO",200000.0,       true)
        );
    }

    @ParameterizedTest(name="TC10 [{index}] id={0} name={1}")
    @MethodSource("employeeData")
    @DisplayName("TC10 - Data-driven: employee record stored completely and correctly")
    void tc_datadriven(int id, String name, String pos,
                       double salary, boolean expected) {
        Employee e = new Employee(id, name, pos, salary);
        assertAll(
            () -> assertEquals(id,     e.getId()),
            () -> assertEquals(name,   e.getName()),
            () -> assertEquals(pos,    e.getPosition()),
            () -> assertEquals(salary, e.getSalary(), 0.001)
        );
    }
}