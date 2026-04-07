package com.greglie.employeemanagement.approach2;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * APPROACH 2: Mock Testing - DAO Layer with Mockito
 * Replaces real database with mock JDBC objects.
 * Quality: Reliability, Isolation
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Approach 2 - Mock Testing: EmployeeDAO")
public class Approach2_MockTest {

    @Mock private Connection        mockConn;
    @Mock private PreparedStatement mockPstmt;
    @Mock private Statement         mockStmt;
    @Mock private ResultSet         mockRs;

    private EmployeeDAO dao;

    @BeforeEach
    void setUp() { dao = new EmployeeDAO(mockConn); }

    @Test
    @DisplayName("TC2.1 - addEmployee uses INSERT SQL statement")
    void tc1_addEmployee_usesInsertSql() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0, "Alice", "Dev", 60000));
        verify(mockConn).prepareStatement(argThat(s ->
            s.toUpperCase().contains("INSERT")));
    }

    @Test
    @DisplayName("TC2.2 - addEmployee binds name to parameter 1")
    void tc2_addEmployee_bindsName() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0, "Alice", "Dev", 60000));
        verify(mockPstmt).setString(1, "Alice");
    }

    @Test
    @DisplayName("TC2.3 - addEmployee binds position to parameter 2")
    void tc3_addEmployee_bindsPosition() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0, "Alice", "Dev", 60000));
        verify(mockPstmt).setString(2, "Dev");
    }

    @Test
    @DisplayName("TC2.4 - addEmployee binds salary to parameter 3")
    void tc4_addEmployee_bindsSalary() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0, "Alice", "Dev", 60000));
        verify(mockPstmt).setDouble(3, 60000.0);
    }

    @Test
    @DisplayName("TC2.5 - updateEmployee uses UPDATE SQL statement")
    void tc5_updateEmployee_usesUpdateSql() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.updateEmployee(new Employee(1, "Alice", "Senior Dev", 70000));
        verify(mockConn).prepareStatement(argThat(s ->
            s.toUpperCase().contains("UPDATE")));
    }

    @Test
    @DisplayName("TC2.6 - updateEmployee binds employee ID to parameter 4")
    void tc6_updateEmployee_bindsId() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.updateEmployee(new Employee(5, "Alice", "Dev", 60000));
        verify(mockPstmt).setInt(4, 5);
    }

    @Test
    @DisplayName("TC2.7 - deleteEmployee uses DELETE SQL statement")
    void tc7_deleteEmployee_usesDeleteSql() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.deleteEmployee(3);
        verify(mockConn).prepareStatement(argThat(s ->
            s.toUpperCase().contains("DELETE")));
    }

    @Test
    @DisplayName("TC2.8 - deleteEmployee binds correct ID")
    void tc8_deleteEmployee_bindsId() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.deleteEmployee(3);
        verify(mockPstmt).setInt(1, 3);
    }

    @Test
    @DisplayName("TC2.9 - getEmployeeById returns Employee when row found")
    void tc9_getById_found() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("id")).thenReturn(1);
        when(mockRs.getString("name")).thenReturn("Alice");
        when(mockRs.getString("position")).thenReturn("Dev");
        when(mockRs.getDouble("salary")).thenReturn(60000.0);
        Employee result = dao.getEmployeeById(1);
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    @DisplayName("TC2.10 - getEmployeeById returns null when no row found")
    void tc10_getById_notFound() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);
        assertNull(dao.getEmployeeById(999));
    }
}