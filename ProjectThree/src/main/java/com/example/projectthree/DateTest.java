package com.example.projectthree;

import static org.junit.Assert.*;

/**
 * Tests the Date class isValid() method.
 * Multiple methods, 5 testing for not valid, 2 testing for valid.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class DateTest {

    /**
     * Checks false output for isValid() method.
     * Checks if there are 29 days in a non-leap year.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testDaysInFebNonLeap() {
        Date date = new Date("2/29/2011");
        assertFalse(date.isValid());
    }

    /**
     * Checks false output for isValid() method.
     * Checks the month provided is valid.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testValidMonth() {
        Date date = new Date("13/20/2023");
        assertFalse(date.isValid());
    }

    /**
     * Checks false output for isValid() method.
     * Checks if a month with 30 days can have 31 days.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testValidDaysOverThirty() {
        Date date = new Date("06/31/2002");
        assertFalse(date.isValid());
    }

    /**
     * Checks false output for isValid() method.
     * Checks if a month with 31 days can have 32 days.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testValidDaysOverThirtyOne() {
        Date date = new Date("08/32/2002");
        assertFalse(date.isValid());
    }

    /**
     * Checks false output for isValid() method.
     * Checks if there are more than 29 days in a leap year for February.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testLeapYrOverMaxDays() {
        Date date = new Date("02/30/2008");
        assertFalse(date.isValid());
    }

    /**
     * Checks false output for checkIfWithinBounds() method.
     * Checks if inputting today's date is correct.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testValidDay() {
        Date date = new Date("10/14/2023");
        assertFalse(date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay()));
    }

    /**
     * Checks false output for checkIfWithinBounds() method.
     * Checks if inputting future date is correct.
     * Should assertFalse as this is not correct.
     */
    @org.junit.Test
    public void testValidYear() {
        Date date = new Date("8/22/2025");
        assertFalse(date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay()));
    }

    /**
     * Checks true output for isValid() method
     * Checks the input of a normal date.
     * Should assertTrue as this is correct.
     */
    @org.junit.Test
    public void testGoodDateWithoutAccountLimits() {
        Date date = new Date("5/15/2022");
        assertTrue(date.isValid());
    }

    /**
     * Checks true output for isValid() method
     * Checks the input of 29th of February in a leap year.
     * Should assertTrue as this is correct.
     */
    @org.junit.Test
    public void testDaysInFebLeap() {
        Date date = new Date("2/29/2008");
        assertTrue(date.isValid());
    }
}