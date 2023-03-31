/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.launcher;

import com.mich.gwan.bookstore.dao.DataAccessObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class BookstoreApplication extends Application {
    private DataAccessObject dataAccessObject;
    @Override
    public void start(Stage stage) throws Exception {
        dataAccessObject = new DataAccessObject();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/mich/gwan/bookstore/login.fxml")));
        stage.setTitle("Bookstore");
        stage.resizableProperty().setValue(false);
        Scene scene = new Scene(root, 746,549, Color.BLUE);
        //scene.setFill(Color.BLUE);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        File file = new File("com/mich/gwan/bookstore/icons/library.png");
        Image image =new Image(file.toURI().toString());
        stage.getIcons().add(image);
        stage.setOnCloseRequest(event -> {
            event.consume();
            cancel(stage);
            try {
                dataAccessObject.deleteSession();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void cancel(Stage stage) {
        //Image image =new Image("com/mich/gwan/extreme/icons/Launcher_Icon.png");
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("");
        cancelAlert.setHeaderText("You are about to quit application.");
        cancelAlert.setContentText("Do you want to Quit?");
        if (cancelAlert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
