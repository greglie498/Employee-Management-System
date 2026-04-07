package com.greglie.employeemanagement.approach9;

import com.greglie.employeemanagement.model.Employee;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * APPROACH 9: Equivalence Partitioning
 * Groups inputs into valid/invalid partitions and tests one from each.
 * Quality: Reliability, Robustness
 */
@DisplayName("Approach 9 - Equivalence Partitioning")
public class Approach9_EquivalenceTest {

    // VALID PARTITION: normal salary values
    @Test @DisplayName("TC9.1 - Valid salary: typical amount 50000") void tc1() {
        Employee e = new Employee(1,"Test","Role",50000); assertEquals(50000, e.getSalary(), 0.001); }

    @Test @DisplayName("TC9.2 - Valid salary: high amount 200000") void tc2() {
        Employee e = new Employee(1,"Test","Role",200000); assertEquals(200000, e.getSalary(), 0.001); }

    @Test @DisplayName("TC9.3 - Valid salary: minimum 0.0") void tc3() {
        Employee e = new Employee(1,"Test","Role",0.0); assertEquals(0.0, e.getSalary(), 0.001); }

    // INVALID PARTITION: negative salary
    @Test @DisplayName("TC9.4 - Invalid salary: negative -1000 (model stores it)") void tc4() {
        Employee e = new Employee(1,"Test","Role",-1000); assertEquals(-1000, e.getSalary(), 0.001); }

    // VALID PARTITION: normal names
    @Test @DisplayName("TC9.5 - Valid name: standard alphabetic name") void tc5() {
        Employee e = new Employee(1,"John Smith","Role",0); assertEquals("John Smith", e.getName()); }

    @Test @DisplayName("TC9.6 - Valid name: single character") void tc6() {
        Employee e = new Employee(1,"A","Role",0); assertEquals("A", e.getName()); }

    // INVALID PARTITION: empty/null name
    @Test @DisplayName("TC9.7 - Invalid name: empty string (model accepts it)") void tc7() {
        Employee e = new Employee(1,"","Role",0); assertEquals("", e.getName()); }

    @Test @DisplayName("TC9.8 - Invalid name: null (model accepts it)") void tc8() {
        Employee e = new Employee(1,null,"Role",0); assertNull(e.getName()); }

    // VALID PARTITION: valid IDs
    @Test @DisplayName("TC9.9 - Valid ID: positive integer 100") void tc9() {
        Employee e = new Employee(100,"Test","Role",0); assertEquals(100, e.getId()); }

    // INVALID PARTITION: negative ID
    @Test @DisplayName("TC9.10 - Invalid ID: negative -5 (model stores it)") void tc10() {
        Employee e = new Employee(-5,"Test","Role",0); assertEquals(-5, e.getId()); }
}