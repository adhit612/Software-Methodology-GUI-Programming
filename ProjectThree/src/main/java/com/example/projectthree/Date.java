package com.example.projectthree;

import java.util.Calendar;

/**
 * Class that declares the classes.Date Event component.
 * Declares a classes.Date by taking in a String.
 * The String gets split into year, month, and day.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public static final int JAN = 0;
    public static final int FEB = 1;
    public static final int MAR = 2;
    public static final int APR = 3;
    public static final int MAY = 4;
    public static final int JUN = 5;
    public static final int JUL = 6;
    public static final int AUG = 7;
    public static final int SEP = 8;
    public static final int OCT = 9;
    public static final int NOV = 10;
    public static final int DEC = 11;

    public static final int FEBLEAPMAX = 29;
    public static final int FEBNONLEAPMAX = 28;
    public static final int THIRTYONEMAX = 31;
    public static final int THIRTYMAX = 30;

    /**
     * Constructor for classes.Date.
     * Declare contact with department and email.
     * @param date input calendar date string.
     */
    public Date(String date) {
        String[] parts = date.split("-");
        this.year = Integer.parseInt(parts[0]);
        this.month = Integer.parseInt(parts[1]) - 1;
        this.day = Integer.parseInt(parts[2]);
    }

    /**
     * Determine if the classes.Date is a valid Calendar date.
     * Only checking if the numbers given exist in Calendar.
     * @return true if the classes.Date is a valid calendar date, false otherwise.
     */
    public boolean isValid() {
        if (!isValidMonth(this.month)) {
            return false;
        }

        if (isLeap(this.year) && this.month == FEB
                && this.day > FEBLEAPMAX) {
            return false;
        } else if (!isLeap(this.year) && this.month == FEB
                && this.day > FEBNONLEAPMAX) {
            return false;
        } else if (hasThirtyOneDays(this.month) && this.day > THIRTYONEMAX) {
            return false;
        } else {
            if (this.day > THIRTYMAX && !hasThirtyOneDays(this.month)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine if the classes.Date's month is supposed to have 31 days.
     * classes.Checking this to ensure that the number entered for the month is valid.
     * @param month the classes.Date's month.
     * @return true if the classes.Date's month has 31 days, false otherwise.
     */
    private boolean hasThirtyOneDays(int month) {
        if (month == JAN || month == MAR || month == MAY || month == JUL
                || month == AUG || month == OCT || month == DEC) {
            return true;
        }
        return false;
    }

    /**
     * Determine if the classes.Date's year is within a leap year.
     * Used in isValid() to check when February is entered.
     * Makes sure February 29th is not entered in a non-leap year.
     * @param year the classes.Date's year.
     * @return true if the classes.Date's year is leap, false otherwise.
     */
    private boolean isLeap(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Determine if the classes.Date's month is a valid number Month.
     * Making sure that a month less than 1 or greater than 12 is entered.
     * @param month the classes.Date's month.
     * @return true if month is a valid number month, false otherwise.
     */
    private boolean isValidMonth(int month) {
        if (month == JAN || month == FEB || month == MAR || month == APR
                || month == MAY || month == JUN || month == JUL
                || month == AUG || month == SEP || month == OCT
                || month == NOV || month == DEC) {
            return true;
        }
        return false;
    }

    /**
     * Determine if the given date is today or in the future.
     * @param month the classes.Date's month.
     * @param year  the classes.Date's year.
     * @param day   the classes.Date's day.
     * @return 1 if the current date is later than the given date, -1 if otherwise, and 0 if they are the same.
     */
    public boolean checkIfWithinBounds(int month, int year, int day) {
        Calendar aheadDate = Calendar.getInstance();
        Calendar currDate = Calendar.getInstance();
        currDate.set(Calendar.DAY_OF_MONTH, day);
        currDate.set(Calendar.MONTH, month);
        currDate.set(Calendar.YEAR, year);
        if (currDate.compareTo(aheadDate) >= 0) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Determine if the given date is in the past.
     * classes.Date cannot be in the past, as it would have already happened.
     * @param month the classes.Date's month.
     * @param year  the classes.Date's year.
     * @param day   the classes.Date's day.
     * @return 1 if given date is in the past, -1/0 otherwise.
     */
    public int checkIfInPast(int month, int year, int day) {
        Calendar currDate = Calendar.getInstance();

        Calendar dateAtHand = Calendar.getInstance();
        dateAtHand.set(Calendar.DAY_OF_MONTH, day);
        dateAtHand.set(Calendar.MONTH, month);
        dateAtHand.set(Calendar.YEAR, year);

        return currDate.compareTo(dateAtHand);
    }
    /**
     * Getter to retrieve today's date.
     * @return today's day in integer format.
     */
    public int getTodaysDate() {
        Calendar current = Calendar.getInstance();
        return current.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Generate a string that properly formats the classes.Date.
     * @return a string containing the toString() message.
     */
    @Override
    public String toString() {
        return this.month + 1 + "/" + this.day + "/" + this.year;
    }

    /**
     * Return the classes.Date's month.
     * @return the classes.Date's month.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Return the classes.Date's year.
     * @return the classes.Date's year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Return the classes.Date's day.
     * @return the classes.Date's day.
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Determine if two Dates are equal.
     * Compare the days and months and years.
     * If any one these values is not equal, the function is false.
     * @param obj a Given object that will be compared to the "this" date if of proper type.
     * @return true if the Dates are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return (date.day == this.day) && (date.month == this.month)
                    && (date.year == this.year);
        }
        return false;
    }

    /**
     * Compare two dates.
     * @param date the classes.Date to be compared.
     * @return 1 if "this" ahead of parameter, -1 if "this" before parameter, 0 if equal.
     */
    @Override
    public int compareTo(Date date) {

        if (this.year < date.year) {
            return -1;
        }
        else if (this.year > date.year) {
            return 1;
        }
        else if (this.month < date.month) {
            return -1;
        }
        else if (this.month > date.month) {
            return 1;
        }
        else if (this.day < date.day) {
            return -1;
        }
        else if (this.day > date.day) {
            return 1;
        }
        else if (this.day == date.day && this.month == date.month
                && this.year == date.year) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
