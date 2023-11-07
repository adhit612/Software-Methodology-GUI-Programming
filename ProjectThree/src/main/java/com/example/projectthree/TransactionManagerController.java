package com.example.projectthree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that manages on action events for UI objects.
 * @author Abhishek Thakare, Adhit Thakur.
 */
public class TransactionManagerController {

    //OPEN / CLOSE tab variables
    @FXML
    private TextArea mainTextArea;

    @FXML
    ToggleGroup AccountTypes;

    @FXML
    ToggleGroup CampusCodes;

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox loyalCheckBox;

    @FXML
    private RadioButton newarkButton;

    @FXML
    private RadioButton nbButton;

    @FXML
    private RadioButton camdenButton;
    //end

    //DEPOSIT / WITHDRAW tab variables
    @FXML
    private ToggleGroup DWAccountTypes;

    @FXML
    private TextField dwFirstNameTextField;

    @FXML
    private TextField dwLastNameTextField;

    @FXML
    private TextField dwAmountTextField;

    @FXML
    private DatePicker dwDatePicker;
    //end

    //Account storage variables
    private Account[] collection = new Account[4];

    AccountDatabase ad = new AccountDatabase(collection, 0);
    //end

    //Constants
    public static final int MM_LIMIT = 2000;
    public static final int MAX_AGE = 24;
    public static final int MIN_AGE = 16;
    public static final int ZERO_CHECK = 0;
    //end

    public Stage stage;

    /**
     * Method that handles the close button account operation.
     * @param actionEvent button click event.
     */
    public void closeButtonAction(ActionEvent actionEvent) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String convDate = "";
        if (datePicker.getValue() == null) {
            mainTextArea.appendText("PLEASE ENTER A DATE\n");
            return;
        }
        else {
            convDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        RadioButton selectedAccount = (RadioButton) AccountTypes.getSelectedToggle();
        if (firstName.isEmpty() || lastName.isEmpty() || selectedAccount == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
        }
        else {
            String accountType = selectedAccount.getText();
            Profile prof = new Profile(firstName, lastName, new Date(convDate));
            Date date = new Date(convDate);
            if (!date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay())) {
                String err = "DOB invalid: " + date.toString()
                        + " cannot be today or a future day.";
                mainTextArea.appendText(err);
            }
            else {
                Account ac = new Checking(prof);
                String type = accountType;
                if (ad.containsForClose(ac, type) == null) {
                    String err = prof.getFname() + " " + prof.getLname() +
                            " " + prof.getDOB() + "(" + accountType + ") is not in the database.\n";
                    mainTextArea.appendText(err);
                }
                else {
                    ad.close(ad.containsForClose(ac, type));
                    String err = prof.getFname() + " " + prof.getLname() + " " +
                            prof.getDOB() + "(" + accountType + ") has been closed.\n";
                    mainTextArea.appendText(err);
                }
            }
        }
    }

    /**
     * Method that handles the open button account operation.
     * @param actionEvent button click event.
     */
    public void openButtonAction(ActionEvent actionEvent) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String date = "";
        String amountStr = amountTextField.getText();
        if (datePicker.getValue() == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
            return;
        }
        else {
            date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        RadioButton selectedAccount = (RadioButton) AccountTypes.getSelectedToggle();
        if (firstName.isEmpty() || lastName.isEmpty() || selectedAccount == null || amountStr.isEmpty()) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
        }
        else {
            String accountType = selectedAccount.getText();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double amountDouble = 0.0;
            try {
                amountDouble = decimalFormat.parse(amountStr).doubleValue();
            } catch (ParseException e) {
                mainTextArea.appendText("PLEASE ENTER A VALID AMOUNT\n");
                e.printStackTrace();
                return;
            }

            if (amountDouble <= ZERO_CHECK) {
                mainTextArea.appendText("INITIAL AMOUNT CANNOT BE 0 OR NEGATIVE\n");
                return;
            }

            oCommandHelper(firstName, lastName, date, collection, ad, decimalFormat, amountDouble, accountType);
        }
    }

    /**
     * Helper for the open event button action.
     * @param firstName first name of the profile.
     * @param lastName last name of the profile.
     * @param dob date of birth for the profile.
     * @param collection collection of accounts that is being managed.
     * @param ad account database that is being manipulated.
     * @param decimalFormat formatter to convert the wanted initial deposit.
     * @param amount the actual initial deposit amount.
     * @param accountType which one of the four account types the account is.
     */
    private void oCommandHelper(String firstName, String lastName, String dob, Account[] collection, AccountDatabase ad, DecimalFormat decimalFormat, double amount, String accountType) {
        Profile prof = new Profile(firstName, lastName, new Date(dob));
        Date date = new Date(dob);
        if (!date.isValid()) {
            String str = "DOB invalid: " + date +
                    " not a valid calendar date!\n";
            mainTextArea.appendText(str);
            return;
        }
        if (!date.checkIfWithinBounds(date.getMonth(), date.getYear(), date.getDay())) {
            String str = "DOB invalid: " + date +
                    " cannot be today or a future day.\n";
            mainTextArea.appendText(str);
            return;
        }
        int age = 2023 - date.getYear();
        int dayDiff = date.getDay() - date.getTodaysDate();
        if (age <= MIN_AGE) {
            String str = "DOB invalid: " + date
                    + " under 16.\n";
            mainTextArea.appendText(str);
            return;
        }
        if (age <= MIN_AGE && dayDiff >= 0) {
            String str = "DOB invalid: "
                    + date + " under 16.\n";
            mainTextArea.appendText(str);
            return;
        }
        oCommandHelperTwo(accountType, collection, ad, amount, prof, date, age);
    }

    /**
     * Additional helper method for the open button action.
     * @param accountType which one of the four account types the account is.
     * @param collection collection of accounts that is being managed.
     * @param ad account database that is being manipulated.
     * @param res double conversion of initial deposit amount.
     * @param prof created profile for the account to be opened.
     * @param date date of the profile.
     * @param age age of the user.
     */
    private void oCommandHelperTwo(String accountType, Account[] collection, AccountDatabase ad, double res, Profile prof, Date date, int age) {
        Account account = null;
        if (accountType.equals("Checking")) {
            account = new Checking(res, prof);
        }
        else if (accountType.equals("College Checking")) {
            if (age >= MAX_AGE) {
                String err = "DOB invalid: " + date.toString() + " over 24.\n";
                mainTextArea.appendText(err);
                return;
            }
            CampusCode campusCode = null;
            RadioButton selectedCampus = (RadioButton) CampusCodes.getSelectedToggle();
            String campusC = selectedCampus.getText();
            if (campusC.isEmpty()) {
                String err = "Please select campus code\n";
                mainTextArea.appendText(err);
                return;
            }
            campusCode = oCommandSetCampusCode(campusC);
            account = new CollegeChecking(res, campusCode, prof);
        }
        else if (accountType.equals("Savings")) {
            boolean isLoyal = false;
            if (loyalCheckBox.isSelected()) {
                isLoyal = true;
            }
            account = new Savings(res, isLoyal, prof);
        }
        else {
            if (res < MM_LIMIT) {
                String err = "Minimum of $2000 to open a Money Market account.\n";
                mainTextArea.appendText(err);
                return;
            }
            else {
                account = new MoneyMarket(res, true, 0, prof);
            }
        }
        oCommandFinalCheck(ad, account, accountType);
    }

    /**
     * Method to determine campus code for college checking.
     * @param campusC campus code associated with account.
     * @return a new campus code that will be stored.
     */
    private CampusCode oCommandSetCampusCode(String campusC) {
        if (campusC.equals("NB")) {
            return CampusCode.ZERO;
        }
        else if (campusC.equals("Newark")) {
            return CampusCode.ONE;
        }
        else if (campusC.equals("Camden")) {
            return CampusCode.TWO;
        }
        else {
            return null;
        }
    }

    /**
     * Final helper method to process open account
     * @param ad account database that is being manipulated.
     * @param account account created that will be added.
     * @param accountType which one of the four account types the account is.
     */
    private void oCommandFinalCheck(AccountDatabase ad, Account account, String accountType) {
        if (ad.contains(account)) {
            String res = account.getProfile().getFname() + " " +
                    account.getProfile().getLname() +
                    " " + account.getProfile().getDOB() + "(" + accountType +
                    ") is already in the database.\n";
            mainTextArea.appendText(res);
        }
        else {
            ad.open(account);
            String res = account.getProfile().getFname() + " " +
                    account.getProfile().getLname() +
                    " " + account.getProfile().getDOB() + "(" +
                    accountType + ") opened.\n";
            mainTextArea.appendText(res);
        }
    }

    /**
     * Method to only show campus buttons if college checking selected.
     * @param actionEvent button click on action.
     */
    public void collegeCheckingButtonAction(ActionEvent actionEvent) {
        newarkButton.setVisible(true);
        nbButton.setVisible(true);
        camdenButton.setVisible(true);
        loyalCheckBox.setVisible(false);
    }

    /**
     * Method to make campus buttons invisible on money market selected.
     * @param actionEvent button click on action.
     */
    public void moneyMarketButtonAction(ActionEvent actionEvent) {
        newarkButton.setVisible(false);
        nbButton.setVisible(false);
        camdenButton.setVisible(false);
        loyalCheckBox.setVisible(false);
    }

    /**
     * Method to make campus buttons invisible on savings selected.
     * @param actionEvent button click on action.
     */
    public void savingsButtonAction(ActionEvent actionEvent) {
        newarkButton.setVisible(false);
        nbButton.setVisible(false);
        camdenButton.setVisible(false);
        loyalCheckBox.setVisible(true);
    }

    /**
     * Method to make campus buttons invisible on checking selected.
     * @param actionEvent button click on action.
     */
    public void checkingButtonAction(ActionEvent actionEvent) {
        newarkButton.setVisible(false);
        nbButton.setVisible(false);
        camdenButton.setVisible(false);
        loyalCheckBox.setVisible(false);
    }

    //DEPOSIT / WITHDRAW TAB
    /**
     * Method to process amount withdraw action.
     * @param actionEvent button click on action.
     */
    public void dwDepositButtonAction(ActionEvent actionEvent) {
        String firstName = dwFirstNameTextField.getText();
        String lastName = dwLastNameTextField.getText();
        String date = "";
        String amountStr = dwAmountTextField.getText();
        if (dwDatePicker.getValue() == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
            return;
        }
        else {
            date = dwDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        RadioButton selectedAccount = (RadioButton) DWAccountTypes.getSelectedToggle();
        if (firstName.isEmpty() || lastName.isEmpty() || amountStr.isEmpty() || selectedAccount == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
        }
        else {
            String accountType = selectedAccount.getText();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double amountDouble = 0.0;
            try {
                amountDouble = decimalFormat.parse(amountStr).doubleValue();
            } catch (ParseException e) {
                mainTextArea.appendText("ENTER IN VALID AMOUNT\n");
                e.printStackTrace();
                return;
            }

            if (amountDouble <= ZERO_CHECK) {
                mainTextArea.appendText("INITIAL TEXT AMOUNT CANNOT BE 0 OR NEGATIVE!\n");
                return;
            }

            Profile prof = new Profile(firstName, lastName, new Date(date));
            String type = accountType;
            Account acc = new Checking(prof);
            depositCommandFinisher(ad, acc, type, prof, amountDouble, accountType);
        }
    }

    /**
     * Helper method to finish amount deposit action.
     * @param ad account database to be selected.
     * @param acc account to be deposited from.
     * @param type type of account.
     * @param prof profile for the account.
     * @param amountDouble amount to be deposited.
     * @param accountType type of account.
     */
    private void depositCommandFinisher(AccountDatabase ad, Account acc, String type, Profile prof, Double amountDouble, String accountType) {
        if (ad.containsForClose(acc, type) == null) {
            dCommandErrorPrinter(prof, accountType);
        }
        else {
            Account account = ad.containsForClose(acc, type);
            double balance = account.getBalance();
            balance += amountDouble;
            account.setBalance(balance);
            ad.deposit(account);
            String succMess = prof.getFname() + " " + prof.getLname() +
                    " " + prof.getDOB() + "(" + accountType +
                    ") Deposit - balance updated.\n";
            mainTextArea.appendText(succMess);
        }
    }

    /**
     * Method to print errors for depositing amount.
     * @param prof profile for the account.
     * @param accountType type of the account.
     */
    private void dCommandErrorPrinter(Profile prof, String accountType) {
        String err = prof.getFname() + " " + prof.getLname() + " " +
                prof.getDOB() + "(" + accountType + ") is not in the database.\n";
        mainTextArea.appendText(err);
    }

    /**
     * Method to process the withdrawal amount action.
     * @param actionEvent button on click action.
     */
    public void dwWithdrawButtonAction(ActionEvent actionEvent) {
        String firstName = dwFirstNameTextField.getText();
        String lastName = dwLastNameTextField.getText();
        String date = "";
        String amountStr = dwAmountTextField.getText();
        if (dwDatePicker.getValue() == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
            return;
        }
        else {
            date = dwDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        RadioButton selectedAccount = (RadioButton) DWAccountTypes.getSelectedToggle();
        if (firstName.isEmpty() || lastName.isEmpty() || amountStr.isEmpty() || selectedAccount == null) {
            mainTextArea.appendText("NOT ALL NEEDED DATA HAS BEEN ENTERED\n");
        }
        else {
            String accountType = selectedAccount.getText();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double amountDouble = 0.0;
            try {
                amountDouble = decimalFormat.parse(amountStr).doubleValue();
            } catch (ParseException e) {
                mainTextArea.appendText("ENTER IN VALID AMOUNT\n");
                e.printStackTrace();
                return;
            }

            if (amountDouble <= ZERO_CHECK) {
                mainTextArea.appendText
                        ("INITIAL TEXT AMOUNT CANNOT BE 0 OR NEGATIVE!\n");
                return;
            }

            Profile prof = new Profile(firstName, lastName, new Date(date));
            String type = accountType;
            Account acc = new Checking(prof);
            wCommandFinisher(acc, prof, type, accountType, amountDouble);
        }
    }

    /**
     * Final helper method to process withdrawal amount action.
     * @param acc account to be deposited from.
     * @param prof profile for the account.
     * @param type type of account.
     * @param accountType type of account.
     * @param amountDouble amount to be deposited.
     */
    private void wCommandFinisher(Account acc, Profile prof, String type, String accountType, Double amountDouble) {
        if (ad.containsForClose(acc, type) == null) {
            wCommandErrorPrinter(prof, accountType);
        }
        else {
            Account account = ad.containsForClose(acc, type);
            double balance = account.getBalance();
            if (balance - amountDouble < 0) {
                wCommandFinalErrorHandler(prof, accountType);
            }
            else {
                balance -= amountDouble;
                account.setBalance(balance);
                ad.withdraw(account);
                if (accountType.equals("Money Market")) {
                    ((MoneyMarket) account).setWithdrawal();
                }
                String finalWithdrawMessage = prof.getFname() + " " +
                        prof.getLname() + " " + prof.getDOB() + "(" +
                        accountType + ") Withdraw - balance updated.\n";
                mainTextArea.appendText(finalWithdrawMessage);
            }
        }
    }

    /**
     * Method to process error cases with amount withdrawal.
     * @param prof profile for the account.
     * @param accountType type of account.
     */
    private void wCommandFinalErrorHandler(Profile prof, String accountType) {
        String err = prof.getFname() + " " + prof.getLname() + " " +
                prof.getDOB() + "(" + accountType +
                ") Withdraw - insufficient fund.\n";
        mainTextArea.appendText(err);
    }

    /**
     * Additional processing for account withdrawal command errors.
     * @param prof profile for the account.
     * @param accountType type of account.
     */
    private void wCommandErrorPrinter(Profile prof, String accountType) {
        String err = prof.getFname() + " " + prof.getLname() + " " +
                prof.getDOB() + "(" + accountType +
                ") is not in the database.\n";
        mainTextArea.appendText(err);
    }
    //END OF DEPOSIT / WITHDRAW TAB

    //BEGIN OF ACCOUNT DATABASE TAB
    /**
     * Method to print all accounts sorted.
     * @param actionEvent button on click action.
     */
    public void adPrintAllAccountsAction(ActionEvent actionEvent) {
        if (ad.getNumAcct() == 0) {
            mainTextArea.appendText("Account Database is empty\n");
        }
        else {
            ad.printSorted(mainTextArea);
        }
    }

    /**
     * Method to print all accounts sorted with fees.
     * @param actionEvent button on click action.
     */
    public void adPrintInterestAndFeesAction(ActionEvent actionEvent) {
        if (ad.getNumAcct() == 0) {
            mainTextArea.appendText("Account Database is empty\n");
        }
        else {
            ad.printFeesAndInterests(mainTextArea);
        }
    }

    /**
     * Method to check for empty database when printing.
     * @param actionEvent button on click action.
     */
    public void adUpdatesAction(ActionEvent actionEvent) {
        if (ad.getNumAcct() == 0) {
            mainTextArea.appendText("Account Database is empty\n");
        }
        else {
            ad.printUpdatedBalances(mainTextArea);
        }
    }

    /**
     * Method to import file and add accounts.
     * @param actionEvent file selector on action.
     */
    public void loadFromFileAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        ArrayList<String> contents = new ArrayList<String>();
        try (Scanner scanner = new Scanner(selectedFile)) {
            while (scanner.hasNext()) {
                contents.add(scanner.next());
            }
        } catch (Exception e) {
            mainTextArea.appendText("No file selected\n");
            return;
        }
        for (int i = 0; i < contents.size(); i++) {
            Account account = null;
            String[] parts = contents.get(i).split(",");
            String firstName = parts[1];
            String lastName = parts[2];
            String date = parts[3];
            String[] dateSplit = date.split("/");
            String dateFin = "";
            dateFin += dateSplit[2];
            dateFin += "-";
            dateFin += dateSplit[0];
            dateFin += "-";
            dateFin += dateSplit[1];
            Profile prof = new Profile(firstName, lastName, new Date(dateFin));
            String amountStr = parts[4];
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double amountDouble = 0.0;
            try {
                amountDouble = decimalFormat.parse(amountStr).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            account = loadCommandHelper(parts, amountDouble, prof);
            ad.open(account);
        }
        mainTextArea.appendText("Accounts loaded.\n");
    }

    /**
     * Helper method to process file contents and add accounts.
     * @param parts text file line to be processed.
     * @param amountDouble amount to be deposited.
     * @param prof profile to be added.
     * @return new account that will be added to account database.
     */
    private Account loadCommandHelper(String[] parts, Double amountDouble, Profile prof) {
        if (parts[0].equals("CC")) {
            CampusCode campusCode = null;
            if (parts[5].equals("0")) {
                campusCode = CampusCode.ZERO;
            } else if (parts[5].equals("1")) {
                campusCode = CampusCode.ONE;
            } else {
                campusCode = CampusCode.TWO;
            }
            return new CollegeChecking(amountDouble, campusCode, prof);
        }
        else if (parts[0].equals("S")) {
            boolean isLoyal = false;
            if (parts[5].equals("1")) {
                isLoyal = true;
            }
            return new Savings(amountDouble, isLoyal, prof);
        }
        else if (parts[0].equals("C")) {
            return new Checking(amountDouble, prof);
        }
        else {
            return new MoneyMarket(amountDouble, true, 0, prof);
        }
    }

    /**
     * Method to process invalid date entered
     * @param actionEvent
     */
    public void datePickerAction(ActionEvent actionEvent) {
        try {
            datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"));
        } catch (Exception e) {
            mainTextArea.appendText("INVALID DATE ENTERED\n");
        }
    }
    //END OF ACCOUNT DATABASE TAB
}