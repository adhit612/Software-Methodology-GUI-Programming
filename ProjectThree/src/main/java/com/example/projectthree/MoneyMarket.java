package com.example.projectthree;

import java.text.DecimalFormat;

/**
 * Money Market Account class which extends Account class.
 * Declares the Money Market class with its specific interest rate and fees.
 * Implements methods from Account class to retrieve various information.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class MoneyMarket extends Savings {
    public static final double INTEREST_RATE = 0.045;

    public static final double INTEREST_RATE_LOYAL = 0.0475;
    public static final int FEE = 25;
    public static final int LIMIT = 2000;
    public static final int MAX_WITHDRAWALS = 3;
    private int withdrawal;

    /**
     * Constructor to initialize a College Checking account.
     * @param balance The balance of the account.
     * @param isLoyal If customer is loyal.
     * @param withdrawal How many times a customer withdrawed from their account.
     * @param prof The profile information of account holder.
     */
    public MoneyMarket(double balance, boolean isLoyal,
                       int withdrawal, Profile prof) {
        super(balance, isLoyal, prof);
        this.withdrawal = withdrawal;
    }

    /**
     * Overridden method from Account class to display interest rate
     * @return the interest rate of the Money Market account, if customer is loyal then the interest rate is higher.
     */
    @Override
    public double monthlyInterest() {
        if(this.balance < LIMIT){
            this.isLoyal = false;
        }
        else{
            this.isLoyal = true;
        }
        if (isLoyal) {
            return INTEREST_RATE_LOYAL;
        }
        else {
            return INTEREST_RATE;
        }
    }

    /**
     * Overridden method from Account class to display fee.
     * If the customer's balance is greater or equal than 2000, they are loyal and have no fees.
     * @return the monthly fee of the account.
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= LIMIT) {
            this.isLoyal = true;
            return 0.0;
        }
        else {
            this.isLoyal = false;
            double finalDeduction = FEE;
            if (this.withdrawal > MAX_WITHDRAWALS) {
                finalDeduction += 10;
            }
            return finalDeduction;
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
     * Getter method to retrieve number of withdrawals for account.
     * @return the number of withdrawals in integer form.
     */
    public int getWithdrawal() {
        return this.withdrawal;
    }

    /**
     * Setter method to set the number of withdrawals for account.
     * Increments the withdrawal count by one.
     */
    public void setWithdrawal() {
        this.withdrawal++;
    }

    /**
     * Resets the withdrawal count to zero.
     */
    public void resetWithdrawal() {
        this.withdrawal = 0;
    }

    /**
     * Gets the account type in string format for ease of processing.
     * @return the String version of account type, Money Market.
     */
    public String returnType() {
        return "Money Market";
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
            return "Money Market::Savings::" + this.getProfile().getFname()
                    + " " + this.getProfile().getLname() + " " +
                    this.getProfile().getDOB().toString() +
                    "::Balance $" + df.format(this.getBalance()) +
                    "::is loyal" + "::withdrawal " + this.getWithdrawal();
        }
        else {
            return "Money Market::Savings::" + this.getProfile().getFname() + " " +
                    this.getProfile().getLname() + " " +
                    this.getProfile().getDOB().toString() +
                    "::Balance $" + df.format(this.getBalance())
                    + "::withdrawal " + this.getWithdrawal();
        }
    }
}
