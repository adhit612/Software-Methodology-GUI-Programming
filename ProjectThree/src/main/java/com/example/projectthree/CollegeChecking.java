package com.example.projectthree;

import java.text.DecimalFormat;

/**
 * College Checking Account class which extends Account class.
 * Declares the College Checking class with its specific interest rate and fees.
 * Implements methods from Account class to retrieve various information.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class CollegeChecking extends Checking {

    private CampusCode campus;
    public static final double INTEREST_RATE = 0.01;
    public static final double FEE = 0.00;

    /**
     * Constructor to initialize a College Checking account.
     * @param balance The balance of the account.
     * @param campus The campus enum to get location of account.
     * @param prof The profile information of account holder.
     */
    public CollegeChecking(double balance, CampusCode campus, Profile prof) {
        super(balance, prof);
        this.campus = campus;
    }

    /**
     * Getter method to retrieve campus of account.
     * @return the campus code for specific college checking account.
     */
    public CampusCode getCampus() {
        return this.campus;
    }

    /**
     * Overridden method from Account class to display interest rate
     * @return the interest rate of the College Checking account.
     */
    @Override
    public double monthlyInterest() {
        return INTEREST_RATE;
    }

    /**
     * Overridden method from Account class to display fee.
     * @return the monthly fee of the account.
     */
    @Override
    public double monthlyFee() {
        return FEE;
    }

    /**
     * Overridden method from Account class to compare the balances of accounts.
     * Compares the balances of two given accounts.
     * @return -1 if account balance is less than given account, 1 if greater, and 0 if equal.
     */
    @Override
    public int compareTo(Account o) {
        if (this.balance < o.balance) {
            return -1;
        }
        else if (this.balance > o.balance) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Overridden method from Account class to print contents of the account.
     * Uses getter methods and their toStrings to display the information.
     * @return the String of all information of the account.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "College Checking::" + this.getProfile().getFname()
                + " " + this.getProfile().getLname() + " " +
                this.getProfile().getDOB().toString() + "::Balance $"
                + df.format(getBalance()) + "::" +
                this.getCampus().toString();
    }

    /**
     * Gets the account type in string format for ease of processing.
     * @return the String version of account type, Checking.
     */
    public String returnType() {
        return "College Checking";
    }
}
