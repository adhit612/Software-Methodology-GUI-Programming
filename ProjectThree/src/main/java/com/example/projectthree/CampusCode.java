package com.example.projectthree;

/**
 * CampusCode enum to declare a CampusCode.
 * Given enum constants, define the campus with its name.
 * @author Abhishek Thakare, Adhit Thakur
 */
public enum CampusCode {
    ZERO {
        /**
         * Display the full ZERO CampusCode through overriding toString().
         * @return a String output that contains the full Campus name.
         */
        @Override
        public String toString() {
            return "NEW_BRUNSWICK";
        }
    },
    ONE {
        /**
         * Display the full ONE CampusCode through overriding toString().
         * @return a String output that contains the full Campus name.
         */
        @Override
        public String toString() {
            return "NEWARK";
        }
    },
    TWO {
        /**
         * Display the full TWO CampusCode through overriding toString().
         * @return a String output that contains the full Campus name.
         */
        @Override
        public String toString() {
            return "CAMDEN";
        }
    }
}
