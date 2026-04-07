package com.greglie.employeemanagement.approach8;

import com.greglie.employeemanagement.dao.EmployeeDAO;
import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * APPROACH 8: Behavior Verification Testing
 * Uses Mockito verify() to confirm methods were called correctly.
 * Quality: Correctness, Maintainability
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Approach 8 - Behavior Verification Testing")
public class Approach8_BehaviorTest {

    @Mock private Connection        mockConn;
    @Mock private PreparedStatement mockPstmt;
    @Mock private Statement         mockStmt;
    @Mock private ResultSet         mockRs;

    private EmployeeDAO dao;

    @BeforeEach
    void setUp() { dao = new EmployeeDAO(mockConn); }

    @Test
    @DisplayName("TC8.1 - addEmployee calls executeUpdate exactly once")
    void tc1() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0,"Alice","Dev",60000));
        verify(mockPstmt, times(1)).executeUpdate();
    }

    @Test
    @DisplayName("TC8.2 - addEmployee never calls executeQuery")
    void tc2() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0,"Alice","Dev",60000));
        verify(mockPstmt, never()).executeQuery();
    }

    @Test
    @DisplayName("TC8.3 - updateEmployee calls setString exactly 2 times")
    void tc3() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.updateEmployee(new Employee(1,"Alice","Dev",60000));
        verify(mockPstmt, times(2)).setString(anyInt(), anyString());
    }

    @Test
    @DisplayName("TC8.4 - updateEmployee calls setInt exactly once (for ID)")
    void tc4() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.updateEmployee(new Employee(1,"Alice","Dev",60000));
        verify(mockPstmt, times(1)).setInt(anyInt(), anyInt());
    }

    @Test
    @DisplayName("TC8.5 - deleteEmployee calls setInt with the correct ID")
    void tc5() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.deleteEmployee(7);
        verify(mockPstmt).setInt(1, 7);
    }

    @Test
    @DisplayName("TC8.6 - deleteEmployee never calls setString")
    void tc6() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.deleteEmployee(7);
        verify(mockPstmt, never()).setString(anyInt(), anyString());
    }

    @Test
    @DisplayName("TC8.7 - getAllEmployees calls createStatement (not prepareStatement)")
    void tc7() throws SQLException {
        when(mockConn.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);
        dao.getAllEmployees();
        verify(mockConn).createStatement();
        verify(mockConn, never()).prepareStatement(anyString());
    }

    @Test
    @DisplayName("TC8.8 - getEmployeeById calls prepareStatement (not createStatement)")
    void tc8() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        when(mockPstmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);
        dao.getEmployeeById(1);
        verify(mockConn).prepareStatement(anyString());
        verify(mockConn, never()).createStatement();
    }

    @Test
    @DisplayName("TC8.9 - addEmployee calls setDouble exactly once for salary")
    void tc9() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.addEmployee(new Employee(0,"Alice","Dev",60000));
        verify(mockPstmt, times(1)).setDouble(anyInt(), anyDouble());
    }

    @Test
    @DisplayName("TC8.10 - updateEmployee calls executeUpdate exactly once")
    void tc10() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
        dao.updateEmployee(new Employee(1,"Alice","Dev",60000));
        verify(mockPstmt, times(1)).executeUpdate();
    }
}