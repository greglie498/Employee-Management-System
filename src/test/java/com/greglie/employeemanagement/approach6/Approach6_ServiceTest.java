package com.greglie.employeemanagement.approach6;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import com.greglie.employeemanagement.service.EmployeeService;
import com.greglie.employeemanagement.util.Conn;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * APPROACH 6: Service Layer Testing (MockedConstruction)
 * Tests business logic in EmployeeService without a real DB.
 * Quality: Reliability, Correctness
 */
@DisplayName("Approach 6 - Service Layer Testing")
public class Approach6_ServiceTest {

    private Connection mockConn;
    private Employee   alice;

    @BeforeEach
    void setUp() {
        mockConn = mock(Connection.class);
        alice = new Employee(1, "Alice", "Engineer", 70000.0);
    }

    @Test
    @DisplayName("TC6.1 - addEmployee returns true on success")
    void tc1() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc =
                 mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().addEmployee(alice));
        }
    }

    @Test
    @DisplayName("TC6.2 - addEmployee returns false when DAO throws")
    void tc2() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> doThrow(new SQLException())
                     .when(m).addEmployee(any()))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertFalse(new EmployeeService().addEmployee(alice));
        }
    }

    @Test
    @DisplayName("TC6.3 - updateEmployee returns true on success")
    void tc3() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc =
                 mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().updateEmployee(alice));
        }
    }

    @Test
    @DisplayName("TC6.4 - updateEmployee returns false when DAO throws")
    void tc4() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> doThrow(new SQLException())
                     .when(m).updateEmployee(any()))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertFalse(new EmployeeService().updateEmployee(alice));
        }
    }

    @Test
    @DisplayName("TC6.5 - removeEmployee returns true on success")
    void tc5() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc =
                 mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().removeEmployee(1));
        }
    }

    @Test
    @DisplayName("TC6.6 - removeEmployee returns false when DAO throws")
    void tc6() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> doThrow(new SQLException())
                     .when(m).deleteEmployee(anyInt()))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertFalse(new EmployeeService().removeEmployee(1));
        }
    }

    @Test
    @DisplayName("TC6.7 - getEmployeeById returns correct employee")
    void tc7() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> when(m.getEmployeeById(1)).thenReturn(alice))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            Employee result = new EmployeeService().getEmployeeById(1);
            assertNotNull(result);
            assertEquals("Alice", result.getName());
        }
    }

    @Test
    @DisplayName("TC6.8 - getEmployeeById returns null when not found")
    void tc8() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> when(m.getEmployeeById(anyInt()))
                     .thenReturn(null))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertNull(new EmployeeService().getEmployeeById(999));
        }
    }

    @Test
    @DisplayName("TC6.9 - getAllEmployees returns full list")
    void tc9() throws SQLException {
        List<Employee> list = List.of(alice,
            new Employee(2,"Bob","Mgr",90000));
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> when(m.getAllEmployees()).thenReturn(list))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertEquals(2,
                new EmployeeService().getAllEmployees().size());
        }
    }

    @Test
    @DisplayName("TC6.10 - getAllEmployees returns null when DAO throws")
    void tc10() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,
                 (m,ctx) -> when(m.getAllEmployees())
                     .thenThrow(new SQLException()))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertNull(new EmployeeService().getAllEmployees());
        }
    }
}