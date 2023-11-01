package com.example.projectthree;
/**
 * Abstract Account class which is used by all types of Account classes.
 * Declares the Account class with its holder and balance variables for each subclass.
 * Maintains getters and setters to access contents of variables.
 * @author Abhishek Thakare, Adhit Thakur
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * Abstract method to be implemented by sub-classes.
     * Sets the monthly interest for a given account.
     * @return the interest value, in double format.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method to be implemented by sub-classes.
     * Sets the monthly fee for a given account.
     * @return the fee value, in double format.
     */
    public abstract double monthlyFee();

    /**
     * Abstract method to be implemented by sub-classes.
     * @return the account type of the specified account.
     */
    public abstract String returnType();

    /**
     * Sets the balance of an account.
     * @param bal given balance to be set.
     */
    public void setBalance(double bal) {
        this.balance = bal;
    }

    /**
     * Gets the balance of an account.
     * @return the balance amount of that account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the profile information of the account holder.
     * @return the profile information of that account.
     */
    public Profile getProfile() {
        return this.holder;
    }

    /**
     * Overridden method to print out contents of specified account type.
     * @return in String format all information of the account.
     */
    @Override
    public abstract String toString();

    /**
     * Overridden method to test if two accounts are equal.
     * @param obj the object to be tested if equal to current account.
     * @return in String format all information of the account, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account acc = (Account) obj;
            return this.getProfile().getDOB().equals(acc.getProfile().getDOB())
                    && this.getProfile().getLname().toLowerCase()
                    .equals(acc.getProfile().getLname().toLowerCase())
                    && this.getProfile().getFname().toLowerCase()
                    .equals(acc.getProfile().getFname().toLowerCase())
                    && this.getBalance() == acc.getBalance();
        }
        return false;
    }
}
