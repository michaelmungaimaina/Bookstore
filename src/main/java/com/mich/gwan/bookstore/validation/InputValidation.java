/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.validation;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public InputValidation(){}

    /**
     * check if textfield have input value
     * @param textField Textfield from which the input is taken
     * @param errorLabel Textfield for displaying the error message
     * @param errorMessage error message input
     * @return true or false
     */
    public boolean isTextFieldEmpty(javafx.scene.control.TextField textField, javafx.scene.control.Label errorLabel, String errorMessage) {
        String value = textField.getText().toString().toUpperCase().trim();
        if (value.isEmpty()) {
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    public boolean isTextFieldEmpty(javafx.scene.control.TextField textField) {
        String value = textField.getText().toString().toUpperCase().trim();
        if (value.isEmpty()) {
            //errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    /**
     * check if the passwordfield is empty
     * @param passwordField passwordfield from which the input is taken
     * @param errorLabel label for displaying the warning text
     * @param errorMessage the actual warning text
     * @return true or false
     */
    public boolean isPasswordFieldEmpty(javafx.scene.control.PasswordField passwordField, javafx.scene.control.Label errorLabel, String errorMessage) {
        String value = passwordField.getText().toString().trim();
        if (value.isEmpty()) {
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    /**
     * check whether choicebox item has been selected
     * @param choiceBox choicebox from which an item is to be selected
     * @param errorLabel label to display the error text
     * @param errorMessage the actual error text
     * @return true or false
     */
    public boolean isChoiceBoxEmpty(ChoiceBox choiceBox, javafx.scene.control.Label errorLabel, String errorMessage){
        if(choiceBox.getValue() == "Select Designation" || choiceBox.getValue() == "Select Gender") {
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }
    /**
     * check whether comboboc item has been selected
     * @param comboBox choicebox from which an item is to be selected
     * @param errorLabel label to display the error text
     * @param errorMessage the actual error text
     * @return true or false
     */
    public boolean isComboBoxEmpty(ChoiceBox comboBox, javafx.scene.control.Label errorLabel, String errorMessage){
        if(comboBox.getValue() == "Transaction Type" ) {
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    /**
     * check whether the provided phone number is valid
     * @param textField textfield to get the input from
     * @param errorLabel display error message onthis label
     * @param errorMessage the error message
     * @return is valid or not valid
     */
    public boolean isPhoneNumberValid(javafx.scene.control.TextField textField, javafx.scene.control.Label errorLabel, String errorMessage){
        String value = textField.getText().toString();
        String password="^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern= Pattern.compile(password);
        Matcher matcher=pattern.matcher(value);
        if (value.isEmpty() || !matcher.matches()){
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    /**
     * check whether the provided password meets the security criteria
     * @param passwordField field from which the input password is got
     * @param errorLabel label to display the warning text
     * @param errorMessage the warning text
     * @return secure or not secure
     */
    public boolean isPasswordSecure(javafx.scene.control.PasswordField passwordField, javafx.scene.control.Label errorLabel, String errorMessage){
        String value = passwordField.getText().toString();
        String password="^(?=.*[0-9]).{5,}$";
        Pattern pattern=Pattern.compile(password);
        Matcher matcher=pattern.matcher(value);
        if (value.isEmpty() || !matcher.matches()){
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
        //(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+,.\\\/;':"-]).{5,}
    }

    /**
     * check whether first password input equals the second password input
     * @param passwordField1 first input passwordfield
     * @param passwordField second input confirm passwordfield
     * @param errorLabel error display label
     * @param errorMessage error message
     * @return true or false
     */
    public boolean isPasswordMatches(javafx.scene.control.PasswordField passwordField1, javafx.scene.control.PasswordField passwordField, javafx.scene.control.Label errorLabel, String errorMessage) {
        String value1 = passwordField1.getText().toString().trim();
        String value2 = passwordField.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            errorPopup(errorLabel,errorMessage);
            return false;
        }
        return true;
    }

    /**
     public boolean isValidEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
     String value = textInputEditText.getText().toString().trim();
     if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
     textInputLayout.setError(message);
     hideKeyboardFrom(textInputEditText);
     return false;
     } else {
     textInputLayout.setErrorEnabled(false);
     }
     return true;
     }


     /**
     * A timer task to display error message for three seconds
     * @param errorLabel the label to which the error is displayed
     * @param errorText the error message
     */
    public void errorPopup(javafx.scene.control.Label errorLabel, String errorText){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            errorLabel.setText("             ");
                            errorLabel.setStyle("-fx-background-color: transparent");
                        });

                    }
                },
                3000
        );
        errorLabel.setStyle("-fx-background-color: #eb4848; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        errorLabel.setText("  "+errorText+"  ");
    }
    /**
     * A timer task to display success message for three seconds
     * @param errorLabel the label to which the error is displayed
     * @param successText the error message
     */
    public void successPopup(javafx.scene.control.Label errorLabel, String successText){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            errorLabel.setText("             ");
                            errorLabel.setStyle("-fx-background-color: transparent");
                        });

                    }
                },
                3000
        );
        errorLabel.setStyle("-fx-background-color: #6ff26f; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        errorLabel.setText("  "+successText+"  ");
    }

    public void warningAlert(String header, String info){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(header);
        alert.setContentText(info);
        alert.showAndWait();
    }
}
