package com.example.projectthree;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label dob;
    @FXML
    private TextField field1;

    @FXML
    protected void firstNameAction() {
        String firstN = field1.getText();
        System.out.println(firstN);
    }
    @FXML
    protected void lastNameAction() {
        lastName.setText("Done!");
    }
    @FXML
    protected void dobAction() {
        dob.setText("Done!");
    }

}