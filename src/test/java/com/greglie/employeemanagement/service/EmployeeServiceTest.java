package com.greglie.employeemanagement.service;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import com.greglie.employeemanagement.util.Conn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for EmployeeService.
 *
 * EmployeeService's constructor calls the static Conn.getConnection() and then
 * constructs a new EmployeeDAO — both of which require a live database under
 * normal circumstances.  Mockito 5's mockStatic / mockConstruction APIs let us
 * intercept those calls so no real database is needed.
 *
 *   mockStatic(Conn.class)       — stubs the static Conn.getConnection() call
 *   mockConstruction(EmployeeDAO.class) — intercepts "new EmployeeDAO(...)" and
 *                                         returns a configurable mock instance
 */
public class EmployeeServiceTest {

    private Connection mockConnection;
    private Employee   alice;
    private Employee   bob;

    public EmployeeServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        mockConnection = mock(Connection.class);
        alice = new Employee(1, "Alice", "Engineer", 5000.0);
        bob   = new Employee(2, "Bob",   "Manager",  7000.0);
    }

    @AfterEach
    public void tearDown() {
        mockConnection = null;
    }

    // ------------------------------------------------------------------ //
    //  addEmployee                                                         //
    // ------------------------------------------------------------------ //

    /**
     * addEmployee() should return true and delegate to EmployeeDAO.addEmployee()
     * when the DAO succeeds.
     */
    @Test
    public void testAddEmployee_success() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class)
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.addEmployee(alice);

            assertTrue(result);
            verify(daoConstruction.constructed().get(0)).addEmployee(alice);
        }
    }

    /**
     * addEmployee() should return false when the DAO throws a SQLException.
     */
    @Test
    public void testAddEmployee_returnsFalseOnSQLException() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> doThrow(new SQLException("Insert failed")).when(mock).addEmployee(any()))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.addEmployee(alice);

            assertFalse(result);
        }
    }

    // ------------------------------------------------------------------ //
    //  updateEmployee                                                      //
    // ------------------------------------------------------------------ //

    /**
     * updateEmployee() should return true and delegate to
     * EmployeeDAO.updateEmployee() when the DAO succeeds.
     */
    @Test
    public void testUpdateEmployee_success() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class)
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.updateEmployee(alice);

            assertTrue(result);
            verify(daoConstruction.constructed().get(0)).updateEmployee(alice);
        }
    }

    /**
     * updateEmployee() should return false when the DAO throws a SQLException.
     */
    @Test
    public void testUpdateEmployee_returnsFalseOnSQLException() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> doThrow(new SQLException("Update failed")).when(mock).updateEmployee(any()))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.updateEmployee(alice);

            assertFalse(result);
        }
    }

    // ------------------------------------------------------------------ //
    //  removeEmployee                                                      //
    // ------------------------------------------------------------------ //

    /**
     * removeEmployee() should return true and delegate to
     * EmployeeDAO.deleteEmployee() with the correct id.
     */
    @Test
    public void testRemoveEmployee_success() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class)
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.removeEmployee(1);

            assertTrue(result);
            verify(daoConstruction.constructed().get(0)).deleteEmployee(1);
        }
    }

    /**
     * removeEmployee() should return false when the DAO throws a SQLException.
     */
    @Test
    public void testRemoveEmployee_returnsFalseOnSQLException() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> doThrow(new SQLException("Delete failed")).when(mock).deleteEmployee(anyInt()))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            boolean result = service.removeEmployee(1);

            assertFalse(result);
        }
    }

    // ------------------------------------------------------------------ //
    //  getEmployeeById                                                     //
    // ------------------------------------------------------------------ //

    /**
     * getEmployeeById() should return the Employee the DAO provides when a
     * match is found.
     */
    @Test
    public void testGetEmployeeById_found() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getEmployeeById(1)).thenReturn(alice))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            Employee result = service.getEmployeeById(1);

            assertNotNull(result);
            assertEquals(1,       result.getId());
            assertEquals("Alice", result.getName());
        }
    }

    /**
     * getEmployeeById() should return null when the DAO finds no matching row.
     */
    @Test
    public void testGetEmployeeById_notFound() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getEmployeeById(anyInt())).thenReturn(null))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            Employee result = service.getEmployeeById(999);

            assertNull(result);
        }
    }

    /**
     * getEmployeeById() should return null (and not throw) when the DAO throws
     * a SQLException — the service swallows it and returns null.
     */
    @Test
    public void testGetEmployeeById_returnsNullOnSQLException() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getEmployeeById(anyInt()))
                        .thenThrow(new SQLException("Query failed")))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            Employee result = service.getEmployeeById(1);

            assertNull(result);
        }
    }

    // ------------------------------------------------------------------ //
    //  getAllEmployees                                                      //
    // ------------------------------------------------------------------ //

    /**
     * getAllEmployees() should return the complete list provided by the DAO.
     */
    @Test
    public void testGetAllEmployees_returnsList() throws SQLException {
        List<Employee> employees = Arrays.asList(alice, bob);

        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getAllEmployees()).thenReturn(employees))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            List<Employee> result = service.getAllEmployees();

            assertNotNull(result);
            assertEquals(2,       result.size());
            assertEquals("Alice", result.get(0).getName());
            assertEquals("Bob",   result.get(1).getName());
        }
    }

    /**
     * getAllEmployees() should return an empty list when the DAO returns one.
     */
    @Test
    public void testGetAllEmployees_emptyList() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getAllEmployees()).thenReturn(List.of()))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            List<Employee> result = service.getAllEmployees();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    /**
     * getAllEmployees() should return null (and not throw) when the DAO throws
     * a SQLException — the service swallows it and returns null.
     */
    @Test
    public void testGetAllEmployees_returnsNullOnSQLException() throws SQLException {
        try (
            MockedStatic<Conn> connStatic = mockStatic(Conn.class);
            MockedConstruction<EmployeeDAO> daoConstruction = mockConstruction(EmployeeDAO.class,
                (mock, ctx) -> when(mock.getAllEmployees())
                        .thenThrow(new SQLException("Query failed")))
        ) {
            connStatic.when(Conn::getConnection).thenReturn(mockConnection);

            EmployeeService service = new EmployeeService();
            List<Employee> result = service.getAllEmployees();

            assertNull(result);
        }
    }
}