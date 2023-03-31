/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewScene {
    public static Object loadNonResizableWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.setY(0);
            stage.setX(0);
            stage.show();
            stage.centerOnScreen();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static Object loadResizableWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setResizable(true);
            stage.setY(0);
            stage.setX(0);
            stage.show();
            stage.centerOnScreen();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    private static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(new File("\\com\\mich\\gwan\\extreme\\icons8_transaction_480px.png").toURI().toString()));
    }
}
