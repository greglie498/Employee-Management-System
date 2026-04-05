package com.greglie.employeemanagement.dao;

import com.greglie.employeemanagement.model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for EmployeeDAO.
 *
 * All JDBC objects (Connection, PreparedStatement, Statement, ResultSet) are
 * mocked with Mockito so no real database is required.
 */
public class EmployeeDAOTest {

    private Connection      mockConn;
    private PreparedStatement mockPstmt;
    private Statement       mockStmt;
    private ResultSet       mockRs;
    private EmployeeDAO     dao;

    public EmployeeDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        mockConn  = mock(Connection.class);
        mockPstmt = mock(PreparedStatement.class);
        mockStmt  = mock(Statement.class);
        mockRs    = mock(ResultSet.class);
        dao = new EmployeeDAO(mockConn);
    }

    @AfterEach
    public void tearDown() {
        dao      = null;
        mockConn = null;
    }

    // ------------------------------------------------------------------ //
    //  addEmployee                                                         //
    // ------------------------------------------------------------------ //

    /**
     * addEmployee() should prepare the INSERT statement and bind all three
     * field values before calling executeUpdate().
     */
    @Test
    public void testAddEmployee() throws SQLException {
        Employee emp = new Employee(0, "Alice", "Engineer", 5000.0);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);

        assertDoesNotThrow(() -> dao.addEmployee(emp));

        verify(mockPstmt).setString(1, "Alice");
        verify(mockPstmt).setString(2, "Engineer");
        verify(mockPstmt).setDouble(3, 5000.0);
        verify(mockPstmt).executeUpdate();
    }

    /**
     * addEmployee() should propagate any SQLException thrown by the driver.
     */
    @Test
    public void testAddEmployee_propagatesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class,
                () -> dao.addEmployee(new Employee(0, "Alice", "Engineer", 5000.0)));
    }

    // ------------------------------------------------------------------ //
    //  updateEmployee                                                      //
    // ------------------------------------------------------------------ //

    /**
     * updateEmployee() should use an UPDATE statement (not INSERT) and bind
     * name, position, salary, and id in that order.
     */
    @Test
    public void testUpdateEmployee() throws SQLException {
        Employee emp = new Employee(1, "Alice", "Senior Engineer", 6500.0);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);

        assertDoesNotThrow(() -> dao.updateEmployee(emp));

        // Capture the SQL string and verify it is an UPDATE
        verify(mockConn).prepareStatement(argThat(sql ->
                sql.toUpperCase().startsWith("UPDATE")));

        verify(mockPstmt).setString(1, "Alice");
        verify(mockPstmt).setString(2, "Senior Engineer");
        verify(mockPstmt).setDouble(3, 6500.0);
        verify(mockPstmt).setInt(4, 1);
        verify(mockPstmt).executeUpdate();
    }

    /**
     * updateEmployee() should propagate any SQLException thrown by the driver.
     */
    @Test
    public void testUpdateEmployee_propagatesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class,
                () -> dao.updateEmployee(new Employee(1, "Alice", "Engineer", 5000.0)));
    }

    // ------------------------------------------------------------------ //
    //  deleteEmployee                                                      //
    // ------------------------------------------------------------------ //

    /**
     * deleteEmployee() should prepare a DELETE statement and bind the id.
     */
    @Test
    public void testDeleteEmployee() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);

        assertDoesNotThrow(() -> dao.deleteEmployee(5));

        verify(mockPstmt).setInt(1, 5);
        verify(mockPstmt).executeUpdate();
    }

    /**
     * deleteEmployee() should propagate any SQLException thrown by the driver.
     */
    @Test
    public void testDeleteEmployee_propagatesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> dao.deleteEmployee(5));
    }

    // ------------------------------------------------------------------ //
    //  getEmployeeById                                                     //
    // ------------------------------------------------------------------ //

    /**
     * getEmployeeById() should return a fully populated Employee when the
     * ResultSet contains a matching row.
     */
    @Test
    public void testGetEmployeeById_found() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("id")).thenReturn(1);
        when(mockRs.getString("name")).thenReturn("Alice");
        when(mockRs.getString("position")).thenReturn("Engineer");
        when(mockRs.getDouble("salary")).thenReturn(5000.0);

        Employee result = dao.getEmployeeById(1);

        assertNotNull(result);
        assertEquals(1,          result.getId());
        assertEquals("Alice",    result.getName());
        assertEquals("Engineer", result.getPosition());
        assertEquals(5000.0,     result.getSalary(), 0.001);
    }

    /**
     * getEmployeeById() should return null when no row matches the given id.
     */
    @Test
    public void testGetEmployeeById_notFound() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        Employee result = dao.getEmployeeById(999);

        assertNull(result);
    }

    /**
     * getEmployeeById() should propagate any SQLException thrown by the driver.
     */
    @Test
    public void testGetEmployeeById_propagatesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> dao.getEmployeeById(1));
    }

    // ------------------------------------------------------------------ //
    //  getAllEmployees                                                      //
    // ------------------------------------------------------------------ //

    /**
     * getAllEmployees() should return a list containing all rows returned
     * by the ResultSet, in order.
     */
    @Test
    public void testGetAllEmployees_returnsMultipleRows() throws SQLException {
        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRs);

        // Simulate two rows then end-of-result-set
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("id")).thenReturn(1, 2);
        when(mockRs.getString("name")).thenReturn("Alice", "Bob");
        when(mockRs.getString("position")).thenReturn("Engineer", "Manager");
        when(mockRs.getDouble("salary")).thenReturn(5000.0, 7000.0);

        List<Employee> result = dao.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("Alice",   result.get(0).getName());
        assertEquals("Bob",     result.get(1).getName());
        assertEquals(5000.0,    result.get(0).getSalary(), 0.001);
        assertEquals(7000.0,    result.get(1).getSalary(), 0.001);
    }

    /**
     * getAllEmployees() should return an empty (non-null) list when the
     * table has no rows.
     */
    @Test
    public void testGetAllEmployees_emptyTable() throws SQLException {
        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        List<Employee> result = dao.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * getAllEmployees() should propagate any SQLException thrown by the driver.
     */
    @Test
    public void testGetAllEmployees_propagatesSQLException() throws SQLException {
        when(mockConn.createStatement()).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> dao.getAllEmployees());
    }
}