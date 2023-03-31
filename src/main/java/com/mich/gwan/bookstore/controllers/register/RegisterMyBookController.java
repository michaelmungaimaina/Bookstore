/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.controllers.register;

import com.mich.gwan.bookstore.dao.DataAccessObject;
import com.mich.gwan.bookstore.models.Book;
import com.mich.gwan.bookstore.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterMyBookController implements Initializable {
    public Button registerButton;
    public Button cancelButton;
    public TextField authorTextField;
    public TextField bookNameTextfield;
    public Label errorLabel;
    public ImageView bookImageView;
    public AnchorPane anchorPane;
    public Button chooseBookPhotoButton;
    public ImageView userIconChooser;

    private File imageFile = null;
    private FileChooser openImage;

    private Stage stage;
    private Parent parent;
    private Scene scene;

    private InputValidation inputValidation;
    private DataAccessObject dataAccessObject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputValidation = new InputValidation();
        dataAccessObject = new DataAccessObject();
        openImage = new FileChooser();
    }

    public void chooseUserIcon(ActionEvent mouseEvent) {
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
            Image bookIcon = new Image(imageFile.toURI().toString(), 100, 100, false, true);
            bookImageView.setImage(bookIcon);
        }
    }

    public static boolean isRequiredIconSize(File file){
        double imageSize=(double)file.length()/1024;
        if(imageSize<2048){
            return true;
        }
        return false;
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/MainWindow.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void registerNewBook(ActionEvent actionEvent) throws SQLException {
        if (!inputValidation.isTextFieldEmpty(bookNameTextfield,errorLabel,"Enter Book name")){
            bookNameTextfield.requestFocus();
            return;
        }
        if (!inputValidation.isTextFieldEmpty(authorTextField,errorLabel,"Enter Author Name")){
            authorTextField.requestFocus();
            return;
        }
        if (imageFile == null){
            inputValidation.errorPopup(errorLabel, "Kindly select image");
            userIconChooser.requestFocus();
            return;
        }
        Book par = new Book();
        par.setBookName(bookNameTextfield.getText().toUpperCase());
        par.setBookAuthor(authorTextField.getText().toUpperCase());
        par.setCategoryName("MY BOOK");
        par.setBookIcon(bookImageView.getImage());
        if (!dataAccessObject.checkBook(par)){
            inputValidation.successPopup(errorLabel,bookNameTextfield.getText().toUpperCase() + " registered successfully");
            dataAccessObject.insertBook(par,imageFile);
            clearFields();
        } else {
            inputValidation.errorPopup(errorLabel,"Book already exists");
        }

    }

    private void clearFields() {
        bookNameTextfield.setText("");
        authorTextField.setText("");
        bookImageView.setImage(null);
        imageFile = null;
    }
}
