/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.controllers.register;

import com.mich.gwan.bookstore.controllers.SceneController;
import com.mich.gwan.bookstore.dao.DataAccessObject;
import com.mich.gwan.bookstore.models.User;
import com.mich.gwan.bookstore.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public ImageView userIconView;
    public ImageView userIconChooser;
    public TextField nameTextField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Button cancelButton;
    public Button registerButton;
    public AnchorPane anchorPane;
    public Label errorLabel;

    private Desktop desktop;
    private FileChooser openImage;
    private File imageFile;

    private Stage stage;
    private Parent parent;
    private Scene scene;

    private InputValidation inputValidation;
    private SceneController sceneController;
    private DataAccessObject dataAccessObject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desktop = Desktop.getDesktop();
        openImage = new FileChooser();
        inputValidation = new InputValidation();
        dataAccessObject = new DataAccessObject();
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/login.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void registerUser(ActionEvent actionEvent) throws SQLException {
        if (!inputValidation.isTextFieldEmpty(nameTextField,errorLabel,"Input your Name!")){
            nameTextField.requestFocus();
            return;
        }
        if (!inputValidation.isPasswordFieldEmpty(passwordField,errorLabel,"Enter your password")){
            passwordField.requestFocus();
            return;
        }
        if (!inputValidation.isPasswordSecure(passwordField,errorLabel,"Weak password!")){
            passwordField.requestFocus();
            return;
        }
        if (!inputValidation.isPasswordFieldEmpty(confirmPasswordField,errorLabel,"Confirm your password")){
            confirmPasswordField.requestFocus();
            return;
        }
        if (!inputValidation.isPasswordMatches(passwordField,confirmPasswordField,errorLabel,"Password missmatch!")){
            confirmPasswordField.requestFocus();
            return;
        }
        User user = new User();
        user.setUserName(nameTextField.getText().toString().toUpperCase());
        user.setUserPassword(passwordField.getText());
        if (!dataAccessObject.checkUser(user)) {
            inputValidation.successPopup(errorLabel, "Registration Successful!");
            dataAccessObject.insertUser(user, imageFile);
            clearFields();
        } else{
            inputValidation.errorPopup(errorLabel, "User Already Exists. Kindly login!");
        }
    }

    void clearFields(){
        passwordField.setText("");
        confirmPasswordField.setText("");
        nameTextField.setText("");
    }

    public void chooseUserIcon(MouseEvent mouseEvent) {
            //set extension filter
            FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            openImage.getExtensionFilters().addAll(jpg, png);
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            //Set to the user's picture directory or user directory if not available
            String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
            imageFile = new File(userDirectoryString);

            //if you cannot navigate to the pictures directory, go to the user home
            if (!imageFile.canRead())
                imageFile = new File(System.getProperty("user.home"));

            openImage.setInitialDirectory(imageFile);
            //show Open dialog
            imageFile = openImage.showOpenDialog(stage);

            if(!isRequiredIconSize(imageFile)){
                inputValidation.warningAlert("Image Size is Big.","Size should not exceed 2MB.");
                return;
            }
            if (imageFile == null) {
                return;
            } else {
                //openImageFile(imageFile);
                String path = imageFile.getAbsolutePath();
                Image attacheeImage = new Image(imageFile.toURI().toString(), 100, 100, false, true);
                userIconView.setImage(attacheeImage);
            }
    }
    public static boolean isRequiredIconSize(File file){
        double imageSize=(double)file.length()/1024;
        if(imageSize<2048){
            return true;
        }
        return false;
    }
}
