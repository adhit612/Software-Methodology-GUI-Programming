package com.example.projectthree;

import java.text.ParseException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

/**
 * Class that manages, updates, manipulates, and displays an Account Database based on client input
 * @author Abhishek Thakare, Adhit Thakur
 */

public class TransactionManager {

    public static final int MM_LIMIT = 2000;
    public static final int MAX_AGE = 24;
    public static final int MIN_AGE = 16;


    /**
     * Method to process O command on null
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void oCommandNull(String [] contents, Account [] collection, AccountDatabase ad) {
        Profile prof = new Profile(contents[2], contents[3], new Date(contents[4]));
        if (contents[1].equals("C")) {
            Account account = new Checking(Double.parseDouble(contents[5]), prof);
            ad.open(account);
            System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(C)" + " opened.");
        }
        else if (contents[1].equals("CC")) {
            CampusCode campusCode = null;
            if (contents[6].equals("0")) {
                campusCode = CampusCode.ZERO;
            }
            else if (contents[6].equals("1")) {
                campusCode = CampusCode.ONE;
            }
            else {
                campusCode = CampusCode.TWO;
            }
            Account account = new CollegeChecking(Double.parseDouble(contents[5]), campusCode, prof);
            ad.open(account);
            System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(CC)" + " opened.");
        }
        else if (contents[1].equals("S")) {
            boolean isLoyal = false;
            if (contents[6].equals("1")) {
                isLoyal = true;
            }
            Account account = new Savings(Double.parseDouble(contents[5]), isLoyal, prof);
            ad.open(account);
            System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(S)" + " opened.");
        }
        else {
            if (Double.parseDouble(contents[5]) < MM_LIMIT) {
                System.out.println("Minimum of $2000 to open a Money Market account.");
            }
            else {
                Account account = new MoneyMarket(Double.parseDouble(contents[5]), true, 0, prof);
                ad.open(account);
                System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(MM)" + " opened.");
            }
        }
    }

    /**
     * Method to process O command after creation of Account Database
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void oCommand(String [] contents, Account [] collection, AccountDatabase ad) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if ((!isNumeric(contents[5]))) {
            System.out.println("Not a valid amount.");
        }
        else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
        }
        else {
            oCommandHelper(contents,collection,ad,decimalFormat);
        }
    }

    /**
     * Method to assist with O command non-null processing
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param decimalFormat Formatter to maintain 2 sig figs
     */
    private void oCommandHelper(String [] contents, Account [] collection, AccountDatabase ad, DecimalFormat decimalFormat) {
        Account account = null;
        Profile prof = new Profile(contents[2], contents[3], new Date(contents[4]));
        Date date = new Date(contents[4]);
        if (!date.isValid()) {
            System.out.println("DOB invalid: " + contents[4] + " not a valid calendar date!");
            return;
        }
        if (!date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay())) {
            System.out.println("DOB invalid: " + contents[4] + " cannot be today or a future day.");
            return;
        }
        int age = 2023 - date.getYear();
        int dayDiff = date.getDay() - date.getTodaysDate();
        if (age <= MIN_AGE && dayDiff >= 0){
            System.out.println("DOB invalid: " + contents[4] + " under 16.");
            return;
        }
        double res = 0.0;
        try {
            res = decimalFormat.parse(contents[5]).doubleValue();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        oCommandHelperTwo(contents, collection, ad, decimalFormat, account, res, prof, date, age);
    }

    /**
     * Second layer method to help with O command non-null processing
     * Splitting up functionality
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param decimalFormat Formatter to maintain 2 sig figs
     * @param account Account that will be added
     * @param res Value that is the current balance
     * @param prof Profile that matches with the current account
     * @param date Date of the current account
     * @param age Age of the person with the account
     */
    private void oCommandHelperTwo(String [] contents, Account [] collection, AccountDatabase ad, DecimalFormat decimalFormat, Account account, double res, Profile prof, Date date, int age) {
        if (contents[1].equals("C")) {
            account = new Checking(res, prof);
        }
        else if (contents[1].equals("CC")) {
            if (age >= MAX_AGE && (date.getDay() - date.getTodaysDate() < 0)) {
                System.out.println("DOB invalid: " + contents[4] + " over 24.");
                return;
            }
            CampusCode campusCode = null;
            if (contents[6].equals("0")) {
                campusCode = CampusCode.ZERO;
            }
            else if (contents[6].equals("1")) {
                campusCode = CampusCode.ONE;
            }
            else if (contents[6].equals("2")) {
                campusCode = CampusCode.TWO;
            }
            else {
                System.out.println("Invalid campus code.");
                return;
            }
            account = new CollegeChecking(res, campusCode, prof);
        }
        else if (contents[1].substring(0, 1).equals("S")) {
            boolean isLoyal = false;
            if (contents[6].equals("1")) {
                isLoyal = true;
            }
            account = new Savings(res, isLoyal, prof);
        }
        else if (contents[1].equals("MM")) {
            if (Double.parseDouble(contents[5]) < MM_LIMIT) {
                System.out.println("Minimum of $2000 to open a Money Market account.");
                return;
            } else {
                account = new MoneyMarket(res, true, 0, prof);
            }
        }
        oCommandFinalCheck(contents,collection,ad,account);
    }

    /**
     * Method to perform final checks and operations on the given account and account database
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param account Account that will be added
     */
    private void oCommandFinalCheck(String [] contents, Account [] collection, AccountDatabase ad, Account account) {
        if (ad.contains(account)) {
            System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(" + contents[1] + ") is already in the database.");
        }
        else {
            ad.open(account);
            if (contents[1].equals("MM") || contents[1].equals("CC")) {
                System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(" + contents[1] + ") opened.");
            }
            else {
                System.out.println(account.getProfile().getFname() + " " + account.getProfile().getLname() + " " + account.getProfile().getDOB() + "(" + contents[1].substring(0, 1) + ") opened.");
            }
        }
    }

    /**
     * Method to perform C command processing
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void cCommand(String [] contents, Account [] collection, AccountDatabase ad) {
        if (contents.length < 5) {
            System.out.println("Missing data for closing an account.");
        }
        else {
            Profile prof = new Profile(contents[2], contents[3], new Date(contents[4]));
            Date date = new Date(contents[4]);
            if (!date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay())) {
                System.out.println("DOB invalid: " + date.toString() + " cannot be today or a future day.");
            }
            else {
                Account ac = new Checking(prof);
                String type = "";
                if (contents[1].equals("C")) {
                    type = "Checking";
                }
                else if (contents[1].equals("CC")) {
                    type = "College Checking";
                }
                else if (contents[1].equals("S")) {
                    type = "Savings";
                }
                else if (contents[1].equals("MM")) {
                    type = "Money Market";
                }
                if (ad.containsForClose(ac, type) == null) {
                    System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") is not in the database.");
                }
                else {
                    ad.close(ad.containsForClose(ac, type));
                    System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") has been closed.");
                }
            }
        }
    }

    /**
     * Methot to set the account type to a String
     * @param contents Line entered in command line
     * @param type Type of the current account (C, CC, etc.)
     * @return String that will be assigned to the passed in type parameter
     */
    private String setType(String [] contents, String type) {
        if (contents[1].equals("CC")) {
            type = "College Checking";
        }
        else if (contents[1].substring(0, 1).equals("C")) {
            type = "Checking";
        }
        else if (contents[1].substring(0, 1).equals("S")) {
            type = "Savings";
        }
        else if (contents[1].equals("MM")) {
            type = "Money Market";
        }
        return type;
    }

    /**
     * Method that prints errors that occur with D commnand data
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param prof Profile of the current account
     */
    private void dCommandErrorPrinter(String [] contents, Account [] collection, AccountDatabase ad, Profile prof) {
        if ((!isNumeric(contents[5]))) {
            System.out.println("Not a valid amount.");
        }
        else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
        }
        else {
            if (contents[1].equals("CC") || contents[1].equals("MM")) {
                System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") is not in the database.");
            }
            else {
                System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1].substring(0, 1) + ") is not in the database.");
            }
        }
    }

    /**
     * Method that processes the D command functionality
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void dCommand(String [] contents, Account [] collection, AccountDatabase ad) {
        Profile prof = new Profile(contents[2], contents[3], new Date(contents[4]));
        String type = "";
        type = setType(contents,type);
        Account acc = new Checking(prof);
        if (ad.containsForClose(acc, type) == null) {
            dCommandErrorPrinter(contents,collection,ad,prof);
        }
        else {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            if ((!isNumeric(contents[5]))) {
                System.out.println("Not a valid amount.");
            }
            else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
                System.out.println("Deposit - amount cannot be 0 or negative.");
            }
            else {
                double res = 0.0;
                try {
                    res = decimalFormat.parse(contents[5]).doubleValue();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                Account account = ad.containsForClose(acc, type);
                double balance = account.getBalance();
                balance += res;
                account.setBalance(balance);
                ad.deposit(account);
                if (contents[1].equals("CC") || contents[1].equals("MM")) {
                    System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") Deposit - balance updated.");
                }
                else {
                    System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1].substring(0, 1) + ") Deposit - balance updated.");
                }
            }
        }
    }

    /**
     * Method to determine if the current account that is trying to withdraw from is invalid
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param prof Profile of the current account
     */
    private void wCommandErrorPrinter(String [] contents, Account [] collection, AccountDatabase ad, Profile prof) {
        if ((!isNumeric(contents[5]))) {
            System.out.println("Not a valid amount.");
        }
        else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
        }
        else {
            if (contents[1].equals("CC") || contents[1].equals("MM")) {
                System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") is not in the database.");
            }
            else {
                System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1].substring(0, 1) + ") is not in the database.");
            }
        }
    }

    /**
     * Method to process the W withdraw command
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void wCommand(String [] contents, Account [] collection, AccountDatabase ad) {
        Profile prof = new Profile(contents[2], contents[3], new Date(contents[4])); String type = ""; type = setType(contents,type);Account acc = new Checking(prof);DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (ad.containsForClose(acc, type) == null) {
            wCommandErrorPrinter(contents,collection,ad,prof);
        }
        else {
            if ((!isNumeric(contents[5]))) {
                System.out.println("Not a valid amount.");
            }
            else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
                System.out.println("Withdraw - amount cannot be 0 or negative.");
            }
            else {
                double res = 0.0;
                try {
                    res = decimalFormat.parse(contents[5]).doubleValue();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                Account account = ad.containsForClose(acc, type);
                double balance = account.getBalance();
                if (balance - res < 0) {
                    wCommandFinalErrorHandler(contents,collection,ad,prof);
                }
                else {
                    balance -= res;
                    account.setBalance(balance);
                    ad.withdraw(account);
                    if (contents[1].equals("CC") || contents[1].equals("MM")) {
                        if (contents[1].equals("MM")) {
                            ((MoneyMarket) account).setWithdrawal();
                        }
                        System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") Withdraw - balance updated.");
                    }
                    else {
                        System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1].substring(0, 1) + ") Withdraw - balance updated.");
                    }
                }
            }
        }
    }

    /**
     * Method to check for any final errors with W command
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @param prof Profile of the current account
     */
    private void wCommandFinalErrorHandler(String [] contents, Account [] collection, AccountDatabase ad, Profile prof) {
        if (contents[1].equals("CC") || contents[1].equals("MM")) {
            System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1] + ") Withdraw - insufficient fund.");
        }
        else {
            System.out.println(prof.getFname() + " " + prof.getLname() + " " + prof.getDOB() + "(" + contents[1].substring(0, 1) + ") Withdraw - insufficient fund.");
        }
    }

    /**
     * Run method to process all commands entered by client
     */
    public void run() {
        Account[] collection = null;
        AccountDatabase ad = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transaction Manager is running.");
        while (true) {
            String line = scanner.nextLine();
            line = line.replace('\t', ' ');
            if (line.equals("Q")) {
                System.out.println("Transaction Manager is terminated.");
                break;
            }
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            String[] contents = new String[tokenizer.countTokens()];
            int w = 0;
            while (tokenizer.hasMoreTokens()) {
                contents[w++] = tokenizer.nextToken();
            }
            if (contents.length == 0) {
            }
            else if(invalidCommandProcessor(contents,collection,ad)){
            }
            else if (contents[0].equals("O") && collection == null) {
                if ((!isNumeric(contents[5]))) {
                    System.out.println("Not a valid amount.");
                }
                else if (isNumeric(contents[5]) && Double.parseDouble(contents[5]) <= 0) {
                    System.out.println("Initial deposit cannot be 0 or negative.");
                }
                else {
                    collection = new Account[4];
                    ad = new AccountDatabase(collection, 0);
                    oCommandNull(contents,collection,ad);
                }
            }
            else{
                commandProcessor(contents,collection,ad);
            }
        }
        scanner.close();
    }

    /**
     * Checking if the given command is invalid, or not of one of the given types
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     * @return true if command is valid, false otherwise
     */
    private boolean invalidCommandProcessor(String [] contents, Account [] collection, AccountDatabase ad) {
        if (!isValidCommand(contents[0])) {
            System.out.println("Invalid command!");
            return true;
        }
        else if (!contents[0].equals("O") && collection == null) {
            System.out.println("Account Database is empty!");
            return true;
        }
        else if (contents[0].equals("O") && collection == null && contents.length < 6) {
            System.out.println("Missing data for opening an account.");
            return true;
        }
        return false;
    }

    /**
     * Method that processes all fundamental commands, then branches into helpers
     * @param contents Line entered in command line
     * @param collection Current collection of Accounts
     * @param ad AccountDatabase holding all current data
     */
    private void commandProcessor(String [] contents, Account [] collection, AccountDatabase ad) {
        if (contents[0].equals("O")) {
            oCommand(contents,collection,ad);
        }
        else if (contents[0].equals("P")) {
            //ad.printSorted();
        }
        else if (contents[0].equals("C")) {
            cCommand(contents,collection,ad);
        }
        else if (contents[0].equals("D")) {
            dCommand(contents,collection,ad);
        }
        else if (contents[0].equals("W")) {
            wCommand(contents,collection,ad);
        }
        else if (contents[0].equals("PI")) {
           //ad.printFeesAndInterests();
        }
        else if (contents[0].equals("UB")) {
            //ad.printUpdatedBalances();
        }
    }

    /**
     * Method that determines if a given String is a number or is of another type
     * @param strNum String that is processed
     * @return true if given String is a number, false otherwise
     */
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Check if the given command is of proper type.
     * Determining if the command is one of the 6 possible inputs.
     * @param str Command to be checked.
     * @return true if command is one of 6 possible inputs, otherwise false.
     */
    private boolean isValidCommand(String str) {
        if (str.equals("O") || str.equals("C") || str.equals("D")
                || str.equals("W") || str.equals("P") || str.equals("PI") || str.equals("UB")) {
            return true;
        }
        return false;
    }
}
