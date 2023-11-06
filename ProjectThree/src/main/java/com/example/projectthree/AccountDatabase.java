package com.example.projectthree;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

/**
 * Class that manages an Array of accounts.
 * Maintains an Account array through open, close, and print operations.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;
    public static final int NOT_FOUND = -1;
    public static final int ARRAY_SIZE_ADDER = 4;

    /**
     * Constructor to initialize an AccountDatabase.
     * @param accounts The array of Accounts, initially size 4.
     * @param numAcct  The number of accounts, initially 0.
     */
    public AccountDatabase(Account[] accounts, int numAcct) {
        this.accounts = accounts;
        this.numAcct = numAcct;
    }

    /**
     * Locate a given Account within the AccountDatabase.
     * @param account the account to be searched for.
     * @return The index of the account if found, NOT_FOUND otherwise.
     */
    private int find(Account account) {
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grow the AccountDatabase if max capacity is reached.
     * If the last element in the AccountDatabase is non-null, the array needs to grow.
     * Makes 4 more spaces for additional Accounts.
     */
    private void grow() {
        Account[] newAccounts = new Account
                [this.accounts.length + ARRAY_SIZE_ADDER];
        for (int i = 0; i < this.accounts.length; i++) {
            newAccounts[i] = this.accounts[i];
        }
        this.accounts = newAccounts;
    }

    /**
     * Check if Account is within the AccountDatabase.
     * @param account The Account to be checked for.
     * @return true if Account is in AccountDatabase, false otherwise.
     */
    public boolean contains(Account account) {
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                return false;
            }
            if (this.accounts[i].equals(account) && ((this.accounts[i]
                    .returnType().equals(account.returnType()) ||
                    ((this.accounts[i].returnType().equals("Checking")
                            && account.returnType()
                            .equals("College Checking")))
                    || ((this.accounts[i].returnType()
                    .equals("College Checking") &&
                    account.returnType().equals("Checking")))))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an account to the AccountDatabase.
     * Checking if valid Account not done here.
     * Simply adding to AccountDatabase.
     * @param account The Account to be added.
     * @return true if added to AccountDatabase, false otherwise.
     */
    public boolean open(Account account) {
        if (this.accounts[this.accounts.length - 1] != null) {
            grow();
        }
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                this.accounts[i] = account;
                this.numAcct++;
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an Account from the AccountDatabase.
     * Given an Account, remove that Account entirely from the AccountDatabase.
     * @param account The Account to be removed.
     * @return true if Account was removed, false otherwise.
     */
    public boolean close(Account account) {
        Account[] newAccounts = new Account[this.accounts.length];
        int j = find(account);
        if (j == NOT_FOUND) {
            return false;
        }
        int k = 0;
        for (int x = 0; x < this.accounts.length; x++) {
            if (x == j) {
                continue;
            }
            newAccounts[k] = this.accounts[x];
            k++;
        }

        this.numAcct--;
        this.accounts = newAccounts;
        return true;
    }

    /**
     * Remove money from balance of specified account.
     * Given an Account, remove the balance given from the input of that account.
     * @param account The Account whose balance is taken away from.
     * @return true if balance can be withdrawn, false otherwise.
     */
    public boolean withdraw(Account account) {
        for (int i = 0; i < this.accounts.length; i++) {
            if(this.accounts[i] == null){
                continue;
            }
            if (this.accounts[i].equals(account)) {
                double currBal = account.getBalance();
                if (currBal - account.getBalance() < 0) {
                    return false;
                }
                else {
                    this.accounts[i].setBalance(currBal);
                }
            }
        }
        return false;
    }

    /**
     * Add money to balance of specified account
     * Given an Account, add the balance given from the input into the account's balance.
     * @param account The Account whose balance is added onto.
     */
    public void deposit(Account account) {
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i].equals(account)) {
                double currBal = account.getBalance();
                this.accounts[i].setBalance(currBal);
                break;
            }
        }
    }

    /**
     * Print accounts from account array sorted by account type and profile
     * Goes in order of: Checking, College Checking, Money Market, Savings.
     * For similar account types, compare last name, then first name.
     */
    public void printSorted(TextArea mainTextArea) {
        mainTextArea.appendText("\n");
        mainTextArea.appendText("*Accounts sorted by account type and profile.\n");

        insertionSortDefault(this.accounts);

        for (int x = 0; x < this.accounts.length; x++) {
            if (this.accounts[x] == null) {
                break;
            }
            mainTextArea.appendText(this.accounts[x].toString());
            mainTextArea.appendText("\n");
        }
        mainTextArea.appendText("*end of list.\n");
        mainTextArea.appendText("\n");
    }

    /**
     * Print accounts from account array with what their interests/fees would be.
     * Follows sorted order of Checking, College Checking, Money Market, then Savings.
     */
    public void printFeesAndInterests(TextArea mainTextArea) {
        mainTextArea.appendText("\n");
        mainTextArea.appendText("*list of accounts with fee and monthly interest\n");
        DecimalFormat df = new DecimalFormat("#,###.00");

        insertionSortFeesAndInterest(this.accounts);

        for (int x = 0; x < this.accounts.length; x++) {
            if (this.accounts[x] == null) {
                break;
            }
            double interest = (this.accounts[x].getBalance()
                    * this.accounts[x].monthlyInterest()) / 12;
            if(this.accounts[x].monthlyFee() < 1 && interest < 1) {
                mainTextArea.appendText(this.accounts[x].toString() + "::fee $" +
                        "0" + df.format(this.accounts[x].monthlyFee()) +
                        "::monthly interest $" + "0" + df.format(interest) + "\n");
            }
            else if(this.accounts[x].monthlyFee() < 1) {
                mainTextArea.appendText(this.accounts[x].toString() + "::fee $" +
                        "0" + df.format(this.accounts[x].monthlyFee()) +
                        "::monthly interest $" + df.format(interest) + "\n");
            }
            else if(interest < 1) {
                mainTextArea.appendText(this.accounts[x].toString() + "::fee $" +
                        df.format(this.accounts[x].monthlyFee()) +
                        "::monthly interest $" + "0" + df.format(interest) + "\n");
            }
            else {
                mainTextArea.appendText(this.accounts[x].toString() + "::fee $" +
                        df.format(this.accounts[x].monthlyFee()) +
                        "::monthly interest $" + df.format(interest) + "\n");
            }
        }
        mainTextArea.appendText("*end of list.\n");
        mainTextArea.appendText("\n");
    }

    /**
     * Print accounts from account array with their actual updated interests/fees/balance.
     * Follows sorted order of Checking, College Checking, Money Market, then Savings.
     */
    public void printUpdatedBalances(TextArea mainTextArea) {
        mainTextArea.appendText("\n");
        mainTextArea.appendText("*list of accounts with fees and " +
                "interests applied.\n");
        for (int x = 0; x < this.accounts.length; x++) {
            if (this.accounts[x] == null) {
                break;
            }
            double interest = (this.accounts[x].getBalance() *
                    this.accounts[x].monthlyInterest()) / 12;
            double fee = this.accounts[x].monthlyFee();
            double currBalance = this.accounts[x].getBalance() -
                    fee + interest;
            this.accounts[x].setBalance(currBalance);
        }

        insertionSortUb(this.accounts);

        for (int x = 0; x < this.accounts.length; x++) {
            if (this.accounts[x] == null) {
                break;
            }
            if (this.accounts[x].returnType().equals("Money Market")) {
                ((MoneyMarket) this.accounts[x]).resetWithdrawal();
            }
            mainTextArea.appendText(this.accounts[x].toString() + "\n");
        }
        mainTextArea.appendText("*end of list.\n");
        mainTextArea.appendText("\n");
    }

    /**
     * Gets the number of accounts in the AccountDatabase.
     * @return number of accounts in the AccountDatabase.
     */
    public int getNumAcct() {
        return this.numAcct;
    }

    /**
     * Gets the account that matches specified account.
     * @return the account that matches the given account, null otherwise
     */
    public Account containsForClose(Account account, String type) {
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                return null;
            }
            if (this.accounts[i].getProfile().getFname().toLowerCase()
                    .equals(account.getProfile().getFname().toLowerCase())
                    && this.accounts[i].getProfile().getLname().toLowerCase()
                    .equals(account.getProfile().getLname().toLowerCase())
                    && this.accounts[i].getProfile().getDOB()
                    .equals(account.getProfile().getDOB()) &&
                    this.accounts[i].returnType().equals(type)) {
                return this.accounts[i];
            }
        }
        return null;
    }

    /**
     * Performs the sorting needed for the "P" command.
     * @param accounts The accounts array to be sorted.
     */
    public void insertionSortDefault(Account [] accounts) {
        for (int i = 1; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            }

            Account temp = this.accounts[i];
            int j = i - 1;
            while (j >= 0 && this.accounts[j].returnType()
                    .compareTo(temp.returnType()) > 0) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            while (j >= 0 && (this.accounts[j].getProfile().getLname()
                    .compareTo(temp.getProfile().getLname()) > 0 &&
                    this.accounts[j].returnType().equals(temp.returnType()))) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            while (j >= 0 && this.accounts[j].getProfile()
                    .getFname().compareTo(temp.getProfile().getFname()) > 0
                    && this.accounts[j].getProfile().getLname()
                    .equals(temp.getProfile().getLname()) && this.accounts[j]
                    .returnType().equals(temp.returnType())) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            this.accounts[j + 1] = temp;
        }
    }

    /**
     * Performs the sorting needed for the "PI" command.
     * @param accounts The accounts array to be sorted.
     */
    public void insertionSortFeesAndInterest(Account [] accounts) {
        for (int i = 1; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            }

            Account temp = this.accounts[i];
            int j = i - 1;
            while (j >= 0 && this.accounts[j].returnType()
                    .compareTo(temp.returnType()) > 0) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            while (j >= 0 && (this.accounts[j].getProfile().getLname()
                    .compareTo(temp.getProfile().getLname()) > 0 &&
                    this.accounts[j].returnType().equals(temp.returnType()))) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            while (j >= 0 && this.accounts[j].getProfile().getFname()
                    .compareTo(temp.getProfile().getFname()) > 0 &&
                    this.accounts[j].getProfile().getLname()
                            .equals(temp.getProfile().getLname())
                    && this.accounts[j].returnType()
                    .equals(temp.returnType())) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            this.accounts[j + 1] = temp;
        }
    }

    /**
     * Performs the sorting needed for the "UB" command.
     * @param accounts The accounts array to be sorted.
     */
    public void insertionSortUb(Account [] accounts){
        for (int i = 1; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            }

            Account temp = this.accounts[i];
            int j = i - 1;
            while (j >= 0 && this.accounts[j].returnType()
                    .compareTo(temp.returnType()) > 0) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            while (j >= 0 && (this.accounts[j].getProfile().getLname()
                    .compareTo(temp.getProfile().getLname()) > 0 &&
                    this.accounts[j].returnType().equals(temp.returnType()))) {
                this.accounts[j + 1] = this.accounts[j];
                j--;
            }

            this.accounts[j + 1] = temp;
        }
    }
}
