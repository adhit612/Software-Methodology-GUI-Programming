package com.example.projectthree;

import java.text.DecimalFormat;

/**
 * Checking Account class which extends Account class.
 * Declares the Checking class with its specific interest rate and fees.
 * Implements methods from Account class to retrieve various information.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class Checking extends Account {
    public static final double INTEREST_RATE = 0.01;
    public static final double FEE = 12;
    public static final double LIMIT = 1000;

    /**
     * Constructor to initialize a Checking account.
     * @param balance The balance of the account.
     * @param prof The profile information of account holder.
     */
    public Checking(double balance, Profile prof) {
        this.balance = balance;
        this.holder = prof;
    }

    /**
     * Second constructor to initialize a Checking account.
     * @param prof The profile information of account holder.
     */
    public Checking(Profile prof) {
        this.holder = prof;
    }

    /**
     * Overridden method from Account class to display interest rate
     * @return the interest rate of the Checking account.
     */
    @Override
    public double monthlyInterest() {
        return INTEREST_RATE;
    }

    /**
     * Overridden method from Account class to display fee.
     * If the balance of account is greater or equal to 1000, the fee is 0.0.
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
        return "Checking::" + this.getProfile().getFname() + " " +
                this.getProfile().getLname() + " " + this.getProfile().getDOB()
                .toString() + "::Balance $" + df.format(getBalance());
    }

    /**
     * Gets the account type in string format for ease of processing.
     * @return the String version of account type, Checking.
     */
    public String returnType() {
        return "Checking";
    }
}
