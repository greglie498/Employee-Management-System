package com.greglie.employeemanagement.approach12;

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
 * APPROACH 12: Integration Testing (Service → DAO chain)
 * Tests the full chain from Service down to DAO using mocked DB.
 * Quality: Reliability, Integration
 */
@DisplayName("Approach 12 - Integration Testing: Service → DAO")
public class Approach12_IntegrationTest {

    private Connection mockConn;

    @BeforeEach
    void setUp() { mockConn = mock(Connection.class); }

    @Test @DisplayName("TC12.1 - Service.addEmployee delegates to DAO.addEmployee")
    void tc1() throws SQLException {
        Employee emp = new Employee(1,"Alice","Dev",60000);
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            new EmployeeService().addEmployee(emp);
            verify(mc.constructed().get(0)).addEmployee(emp);
        }
    }

    @Test @DisplayName("TC12.2 - Service.updateEmployee delegates to DAO.updateEmployee")
    void tc2() throws SQLException {
        Employee emp = new Employee(1,"Alice","Dev",60000);
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            new EmployeeService().updateEmployee(emp);
            verify(mc.constructed().get(0)).updateEmployee(emp);
        }
    }

    @Test @DisplayName("TC12.3 - Service.removeEmployee delegates to DAO.deleteEmployee")
    void tc3() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(EmployeeDAO.class)) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            new EmployeeService().removeEmployee(5);
            verify(mc.constructed().get(0)).deleteEmployee(5);
        }
    }

    @Test @DisplayName("TC12.4 - Service.getEmployeeById delegates to DAO.getEmployeeById")
    void tc4() throws SQLException {
        Employee alice = new Employee(1,"Alice","Dev",60000);
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getEmployeeById(1)).thenReturn(alice))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertEquals("Alice",
                new EmployeeService().getEmployeeById(1).getName());
        }
    }

    @Test @DisplayName("TC12.5 - Service.getAllEmployees delegates to DAO.getAllEmployees")
    void tc5() throws SQLException {
        List<Employee> list = List.of(
            new Employee(1,"Alice","Dev",60000),
            new Employee(2,"Bob","Mgr",80000));
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getAllEmployees()).thenReturn(list))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertEquals(2,
                new EmployeeService().getAllEmployees().size());
        }
    }

    @Test @DisplayName("TC12.6 - Service add then get returns same employee")
    void tc6() throws SQLException {
        Employee emp = new Employee(1,"Alice","Dev",60000);
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getEmployeeById(1)).thenReturn(emp))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            EmployeeService svc = new EmployeeService();
            svc.addEmployee(emp);
            Employee fetched = svc.getEmployeeById(1);
            assertEquals("Alice", fetched.getName());
        }
    }

    @Test @DisplayName("TC12.7 - Service returns empty list when no employees exist")
    void tc7() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getAllEmployees()).thenReturn(List.of()))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertTrue(new EmployeeService().getAllEmployees().isEmpty());
        }
    }

    @Test @DisplayName("TC12.8 - Service returns null when getById finds nothing")
    void tc8() throws SQLException {
        try (MockedStatic<Conn> cs = mockStatic(Conn.class);
             MockedConstruction<EmployeeDAO> mc = mockConstruction(
                 EmployeeDAO.class,(m,ctx) ->
                     when(m.getEmployeeById(anyInt())).thenReturn(null))) {
            cs.when(Conn::getConnection).thenReturn(mockConn);
            assertNull(new EmployeeService().getEmployeeById(999));
        }
    }
}