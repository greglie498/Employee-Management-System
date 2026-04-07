package com.greglie.employeemanagement.approach15;

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
 * APPROACH 15: Regression / Smoke Testing
 * Verifies core CRUD operations still work after any code changes.
 * Quality: Reliability, Stability
 */
@DisplayName("Approach 15 - Regression / Smoke Testing")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Approach15_RegressionTest {

    private Connection mockConn;
    private Employee   emp;

    @BeforeEach
    void setUp() {
        mockConn = mock(Connection.class);
        emp = new Employee(1,"Regression User","Tester",55000.0);
    }

    @Test @Order(1) @DisplayName("TC15.1 - SMOKE: Employee model can be created")
    void tc1() { assertNotNull(emp); assertEquals("Regression User", emp.getName()); }

    @Test @Order(2) @DisplayName("TC15.2 - SMOKE: Employee name can be updated")
    void tc2() { emp.setName("Updated User"); assertEquals("Updated User", emp.getName()); }

    @Test @Order(3) @DisplayName("TC15.3 - SMOKE: Employee salary can be updated")
    void tc3() { emp.setSalary(70000); assertEquals(70000, emp.getSalary(), 0.001); }

    @Test @Order(4) @DisplayName("TC15.4 - REGRESSION: addEmployee returns true (no regression)")
    void tc4() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().addEmployee(emp));
        }
    }

    @Test @Order(5) @DisplayName("TC15.5 - REGRESSION: updateEmployee returns true (no regression)")
    void tc5() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().updateEmployee(emp));
        }
    }

    @Test @Order(6) @DisplayName("TC15.6 - REGRESSION: removeEmployee returns true (no regression)")
    void tc6() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().removeEmployee(1));
        }
    }

    @Test @Order(7) @DisplayName("TC15.7 - REGRESSION: getEmployeeById returns correct data")
    void tc7() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getEmployeeById(1)).thenReturn(emp))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            Employee result = new EmployeeService().getEmployeeById(1);
            assertEquals("Regression User", result.getName());
        }
    }

    @Test @Order(8) @DisplayName("TC15.8 - REGRESSION: getAllEmployees returns non-null list")
    void tc8() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getAllEmployees()).thenReturn(List.of(emp)))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            List<Employee> list = new EmployeeService().getAllEmployees();
            assertNotNull(list);
            assertFalse(list.isEmpty());
        }
    }

    @Test @Order(9) @DisplayName("TC15.9 - REGRESSION: DAO addEmployee uses INSERT SQL")
    void tc9() throws SQLException {
        Connection c = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(ps);
        EmployeeDAO dao = new EmployeeDAO(c);
        dao.addEmployee(emp);
        verify(c).prepareStatement(argThat(s -> s.toUpperCase().contains("INSERT")));
    }

    @Test @Order(10) @DisplayName("TC15.10 - REGRESSION: DAO deleteEmployee uses DELETE SQL")
    void tc10() throws SQLException {
        Connection c = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(ps);
        EmployeeDAO dao = new EmployeeDAO(c);
        dao.deleteEmployee(1);
        verify(c).prepareStatement(argThat(s -> s.toUpperCase().contains("DELETE")));
    }
}