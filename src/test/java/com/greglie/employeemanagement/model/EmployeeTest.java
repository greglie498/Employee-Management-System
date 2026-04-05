package com.greglie.employeemanagement.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Employee model class.
 * No DB or mocking needed — this is a plain POJO.
 */
public class EmployeeTest {

    // A shared instance re-created before every test
    private Employee instance;

    public EmployeeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Construct a fresh Employee before every test
        instance = new Employee(1, "Alice", "Engineer", 5000.0);
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    // ------------------------------------------------------------------ //
    //  Constructor                                                         //
    // ------------------------------------------------------------------ //

    /**
     * Verify that the 4-arg constructor stores every field correctly.
     */
    @Test
    public void testConstructor_setsAllFields() {
        assertEquals(1,          instance.getId());
        assertEquals("Alice",    instance.getName());
        assertEquals("Engineer", instance.getPosition());
        assertEquals(5000.0,     instance.getSalary(), 0.001);
    }

    // ------------------------------------------------------------------ //
    //  getId / setId                                                       //
    // ------------------------------------------------------------------ //

    /**
     * getId() should return the value supplied to the constructor.
     */
    @Test
    public void testGetId() {
        assertEquals(1, instance.getId());
    }

    /**
     * setId() should update the id field; subsequent getId() should reflect it.
     */
    @Test
    public void testSetId() {
        instance.setId(42);
        assertEquals(42, instance.getId());
    }

    // ------------------------------------------------------------------ //
    //  getName / setName                                                   //
    // ------------------------------------------------------------------ //

    /**
     * getName() should return the value supplied to the constructor.
     */
    @Test
    public void testGetName() {
        assertEquals("Alice", instance.getName());
    }

    /**
     * setName() should update the name field.
     */
    @Test
    public void testSetName() {
        instance.setName("Bob");
        assertEquals("Bob", instance.getName());
    }

    /**
     * setName() should accept an empty string without throwing.
     */
    @Test
    public void testSetName_emptyString() {
        instance.setName("");
        assertEquals("", instance.getName());
    }

    // ------------------------------------------------------------------ //
    //  getPosition / setPosition                                           //
    // ------------------------------------------------------------------ //

    /**
     * getPosition() should return the value supplied to the constructor.
     */
    @Test
    public void testGetPosition() {
        assertEquals("Engineer", instance.getPosition());
    }

    /**
     * setPosition() should update the position field.
     */
    @Test
    public void testSetPosition() {
        instance.setPosition("Manager");
        assertEquals("Manager", instance.getPosition());
    }

    // ------------------------------------------------------------------ //
    //  getSalary / setSalary                                               //
    // ------------------------------------------------------------------ //

    /**
     * getSalary() should return the value supplied to the constructor.
     */
    @Test
    public void testGetSalary() {
        assertEquals(5000.0, instance.getSalary(), 0.001);
    }

    /**
     * setSalary() should update the salary field.
     */
    @Test
    public void testSetSalary() {
        instance.setSalary(9500.50);
        assertEquals(9500.50, instance.getSalary(), 0.001);
    }

    /**
     * setSalary() should accept zero without throwing.
     */
    @Test
    public void testSetSalary_zero() {
        instance.setSalary(0.0);
        assertEquals(0.0, instance.getSalary(), 0.001);
    }

    /**
     * setSalary() should accept a negative value (business rules are
     * enforced elsewhere; the model should not silently reject it).
     */
    @Test
    public void testSetSalary_negative() {
        instance.setSalary(-100.0);
        assertEquals(-100.0, instance.getSalary(), 0.001);
    }
}