package com.example.projectthree;

/**
 * Profile class created to hold all information about bank account customer.
 * Contains a first name, last name, and date of birth to identify the customer.
 * Implements getters and setters to get certain information about variables.
 * @author Abhishek Thakare, Adhit Thakur
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor to initialize a Profile for the account.
     * @param fname String to hold the first name of the account holder.
     * @param lname String to hold the last name of the account holder.
     * @param dob Date from date class to hold date of birth of account holder
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Overridden method from Account class to compare the Profiles of two accounts.
     * Compares the last names, then first names, then dob.
     * @return -1 if current Profile is alphabetically less than the given Profile, 1 otherwise, and 0 if the same.
     */
    @Override
    public int compareTo(Profile o) {
        if (this.lname.toLowerCase().compareTo(o.lname.toLowerCase()) < 0) {
            return -1;
        }
        else if (this.lname.toLowerCase().compareTo
                (o.lname.toLowerCase()) > 0) {
            return 1;
        }
        else if (this.fname.toLowerCase().compareTo
                (o.fname.toLowerCase()) < 0) {
            return -1;
        }
        else if (this.fname.toLowerCase().compareTo
                (o.fname.toLowerCase()) > 0) {
            return 1;
        }
        else {
            if (this.dob.compareTo(o.dob) < 0) {
                return -1;
            } else if (this.dob.compareTo(o.dob) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Getter method to retrieve the last name of the account holder.
     * @return the last name of the holder in String format.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Getter method to retrieve the first name of the account holder.
     * @return the first name of the holder in String format.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Getter method to retrieve the date of birth of the account holder.
     * @return the dob of the holder in String format.
     */
    public Date getDOB() {
        return this.dob;
    }

    /**
     * Overridden method from Account class to check if two Profiles are equal.
     * Checks if the last names, first names, and date of births are equal in both Profiles.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile prof = (Profile) obj;
            return this.getLname().equals(prof.getLname()) && this.getFname()
                    .equals(prof.getFname()) && this.getDOB()
                    .equals(prof.getDOB());
        }
        return false;
    }
}
