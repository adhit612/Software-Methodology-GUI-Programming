package com.example.projectthree;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the AccountDatabase class close() method.
 * One method for true output, one method for false output.
 *
 * @author Abhishek Thakare, Adhit Thakur
 */
public class AccountDatabaseTest {

    /**
     * Checks if the account is closed successfully from the Account Database.
     * Opens the account first, then closes the account.
     */
    @Test
    public void closeTrue() {
        Profile prof1 = new Profile("Bob", "Ross", new Date("7/4/2003"));
        Account checkAcct = new Checking(prof1);
        Account[] accounts = new Account[4];
        AccountDatabase db = new AccountDatabase(accounts, 4);
        db.open(checkAcct);
        assertTrue(db.close(checkAcct));
    }

    /**
     * Checks if the account is closed incorrectly from the Account Database.
     * Tries to close an account that is not added to the Account Database.
     */
    @Test
    public void closeFalse() {
        Profile prof2 = new Profile("Lionel", "Messi", new Date("10/20/2003"));
        Account savingsAcct = new Savings(100.00, true, prof2);
        Account[] accounts = new Account[4];
        AccountDatabase db = new AccountDatabase(accounts, 1);
        boolean b = true;
        try {
            db.close(savingsAcct);
        }
        catch (NullPointerException e) {
            b = false;
        }
        assertFalse(b);
    }
}