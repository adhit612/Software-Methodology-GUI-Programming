package com.example.projectthree;

import java.text.DecimalFormat;

/**
 * Savings Account class which extends Account class.
 * Declares the Savings class with its specific interest rate and fees.
 * Implements methods from Account class to retrieve various information.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class Savings extends Account {
    public static final double INTEREST_RATE = 0.04;
    public static final double FEE = 25;
    public static final int LIMIT = 500;
    protected boolean isLoyal;

    /**
     * Constructor to initialize a Savings account.
     * @param balance The balance of the account.
     * @param isLoyal If the customer is loyal in their account.
     * @param prof The profile information of account holder.
     */
    public Savings(double balance, boolean isLoyal, Profile prof) {
        this.balance = balance;
        this.isLoyal = isLoyal;
        this.holder = prof;
    }

    /**
     * Getter method to see if the account holder is loyal.
     * @return true is they are loyal, false if not.
     */
    public boolean getLoyal() {
        return this.isLoyal;
    }

    /**
     * Overridden method from Account class to display interest rate
     * @return the interest rate of the Savings account, if customer is loyal then the interest rate is higher.
     */
    @Override
    public double monthlyInterest() {
        double finalRate = INTEREST_RATE;
        if (isLoyal) {
            finalRate += 0.0025;
            return finalRate;
        }
        return finalRate;
    }

    /**
     * Overridden method from Account class to display fee.
     * If the customer's balance is greater or equal than 500, they have no fees.
     * @return the monthly fee of the account.
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= LIMIT) {
            return 0.0;
        }
        else {
            return FEE;
        }
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
        if (this.getLoyal()) {
            return "Savings::" + this.getProfile().getFname() + " " +
                    this.getProfile().getLname() + " " + this.getProfile()
                    .getDOB().toString() + "::Balance $" +
                    df.format(this.getBalance()) + "::is loyal";
        }
        else {
            return "Savings::" + this.getProfile().getFname() + " " +
                    this.getProfile().getLname() + " " + this.getProfile()
                    .getDOB().toString() + "::Balance $" +
                    df.format(getBalance());
        }

    }

    /**
     * Gets the account type in string format for ease of processing.
     * @return the String version of account type, Savings.
     */
    public String returnType() {
        return "Savings";
    }
}
