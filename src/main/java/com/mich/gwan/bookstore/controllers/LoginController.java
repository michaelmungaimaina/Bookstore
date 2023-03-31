package com.mich.gwan.bookstore.controllers;

import com.mich.gwan.bookstore.dao.DataAccessObject;
import com.mich.gwan.bookstore.models.User;
import com.mich.gwan.bookstore.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    public AnchorPane anchorPane;
    public TextField userName;
    public PasswordField passwordField;
    public Button loginButton;
    public Button registerButton;
    public Label errorLabel;
    private Connection conn=null;
    private Statement stm=null;
    private ResultSet rs=null;
    private PreparedStatement ps=null;
    private Stage stage;
    private Parent parent;
    private Scene scene;
    private User user;
    private InputValidation inputValidation;
    private DataAccessObject dataAccessObject;
    private NewScene newScene;
    private SceneController sceneController;
    private int counter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputValidation = new InputValidation();
        dataAccessObject = new DataAccessObject();
        user = new User();
    }

    public void register(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/register.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void login(ActionEvent actionEvent) throws SQLException, IOException {
         if (!inputValidation.isTextFieldEmpty(userName,errorLabel,"Input your Name!")){
         userName.requestFocus();
         return;
         }
         if (!inputValidation.isPasswordFieldEmpty(passwordField,errorLabel,"Enter your password")){
         passwordField.requestFocus();
         return;
         }
         user.setUserPassword(passwordField.getText());
         user.setUserName(userName.getText().toUpperCase());
         user.setUserIcon(dataAccessObject.getUser(userName.getText().toUpperCase(),passwordField.getText()));
         if (dataAccessObject.checkUser(user)) {
             //create logged session
             dataAccessObject.deleteSession();
             dataAccessObject.insertSession(user);
             inputValidation.successPopup(errorLabel, "Login successful!");
             clearFields();
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mich/gwan/bookstore/MainWindow.fxml"));
             Parent root = (Parent) fxmlLoader.load();
             MainController controller = (MainController) fxmlLoader.getController();
             controller.loggedUser(user);
             stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
             stage.resizableProperty().setValue(true);
             scene = new Scene(root, 273, 387);//heigh, width
             stage.setScene(scene);
             stage.setY(0);
             stage.setX(0);
             stage.show();
             stage.centerOnScreen();
         }
    else {
     inputValidation.errorPopup(errorLabel, "Kindly enter valid login credentials!");
     counter ++;
     }
     if (counter >= 3){
     Alert loginAlert = new Alert(Alert.AlertType.WARNING);
     loginAlert.setTitle("WARNING");
     loginAlert.setHeaderText("Too Many Attempts!");
     loginAlert.setContentText("Kindly contact your admin for assistance");
     loginAlert.showAndWait();
     }
     }
     void clearFields(){
        userName.setText("");
        passwordField.setText("");
     }
}
