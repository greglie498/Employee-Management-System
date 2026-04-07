package com.greglie.employeemanagement.approach5;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * APPROACH 5: Exception / Error Path Testing
 * Verifies every DAO method throws SQLException correctly.
 * Quality: Reliability, Fault Tolerance
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Approach 5 - Exception Path Testing")
public class Approach5_ExceptionTest {

    @Mock private Connection        mockConn;
    @Mock private PreparedStatement mockPstmt;
    @Mock private Statement         mockStmt;

    private EmployeeDAO dao;

    @BeforeEach
    void setUp() { dao = new EmployeeDAO(mockConn); }

    @Test
    @DisplayName("TC5.1 - addEmployee throws when prepareStatement fails")
    void tc1() throws SQLException {
        when(mockConn.prepareStatement(anyString()))
            .thenThrow(new SQLException("Connection lost"));
        assertThrows(SQLException.class,
            () -> dao.addEmployee(new Employee(0,"X","Y",0)));
    }

    @Test
    @DisplayName("TC5.2 - addEmployee throws when executeUpdate fails")
    void tc2() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeUpdate()).thenThrow(new SQLException("Constraint"));
        assertThrows(SQLException.class,
            () -> dao.addEmployee(new Employee(0,"X","Y",0)));
    }

    @Test
    @DisplayName("TC5.3 - updateEmployee throws when prepareStatement fails")
    void tc3() throws SQLException {
        when(mockConn.prepareStatement(anyString()))
            .thenThrow(new SQLException("Timeout"));
        assertThrows(SQLException.class,
            () -> dao.updateEmployee(new Employee(1,"X","Y",0)));
    }

    @Test
    @DisplayName("TC5.4 - updateEmployee throws when executeUpdate fails")
    void tc4() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeUpdate()).thenThrow(new SQLException("Deadlock"));
        assertThrows(SQLException.class,
            () -> dao.updateEmployee(new Employee(1,"X","Y",0)));
    }

    @Test
    @DisplayName("TC5.5 - deleteEmployee throws when prepareStatement fails")
    void tc5() throws SQLException {
        when(mockConn.prepareStatement(anyString()))
            .thenThrow(new SQLException("Network error"));
        assertThrows(SQLException.class, () -> dao.deleteEmployee(1));
    }

    @Test
    @DisplayName("TC5.6 - deleteEmployee throws when executeUpdate fails")
    void tc6() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeUpdate()).thenThrow(new SQLException("FK violation"));
        assertThrows(SQLException.class, () -> dao.deleteEmployee(1));
    }

    @Test
    @DisplayName("TC5.7 - getEmployeeById throws when prepareStatement fails")
    void tc7() throws SQLException {
        when(mockConn.prepareStatement(anyString()))
            .thenThrow(new SQLException("Auth failure"));
        assertThrows(SQLException.class, () -> dao.getEmployeeById(1));
    }

    @Test
    @DisplayName("TC5.8 - getEmployeeById throws when executeQuery fails")
    void tc8() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenThrow(new SQLException("Query timeout"));
        assertThrows(SQLException.class, () -> dao.getEmployeeById(1));
    }

    @Test
    @DisplayName("TC5.9 - getAllEmployees throws when createStatement fails")
    void tc9() throws SQLException {
        when(mockConn.createStatement())
            .thenThrow(new SQLException("Closed connection"));
        assertThrows(SQLException.class, () -> dao.getAllEmployees());
    }

    @Test
    @DisplayName("TC5.10 - getAllEmployees throws when executeQuery fails")
    void tc10() throws SQLException {
        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery(anyString()))
            .thenThrow(new SQLException("Table not found"));
        assertThrows(SQLException.class, () -> dao.getAllEmployees());
    }
}