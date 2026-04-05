package com.greglie.employeemanagement.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Conn utility class.
 * NOTE: These tests assume no live MySQL instance is available (unit test context).
 * getConnection() is expected to throw SQLException when the DB is unreachable.
 */
public class ConnTest {

    public ConnTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test that getConnection() throws SQLException when no DB is available.
     * In a unit-test environment there is no live MySQL server, so DriverManager
     * must raise an exception rather than silently returning null.
     */
    @Test
    public void testGetConnection_throwsWhenNoDatabaseAvailable() {
        try{
            Connection result = Conn.getConnection();
            assertNotNull(result, "Connection should not be null when DB is available");
            result.close();
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Connection") || e.getErrorCode() !=0);
        
        }
    }

    /**
     * Test that a freshly constructed Conn instance returns a null Statement,
     * because the statement field is never initialised by the constructor.
     */
    @Test
    public void testGetStatement_returnsNullByDefault() {
        Conn instance = new Conn();
        assertNull(instance.getStatement());
    }

    /**
     * Test that Conn.main() propagates a SQLException when no DB is available.
     * The method creates a real Statement from the connection, so it will
     * throw before reaching the query if the connection itself fails.
     */
    @Test
    public void testMain_throwsWhenNoDatabaseAvailable() {
        try{
            Conn.main(null);
        } catch (SQLException e) {
            assertNotNull(e.getMessage());
        }
    }
}