module com.example.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.swing;


    opens com.mich.gwan.bookstore to javafx.fxml;
    exports com.mich.gwan.bookstore.dao;
    opens com.mich.gwan.bookstore.dao to javafx.fxml;
    exports com.mich.gwan.bookstore.controllers;
    opens com.mich.gwan.bookstore.controllers to javafx.fxml;
    exports com.mich.gwan.bookstore.launcher;
    opens com.mich.gwan.bookstore.launcher to javafx.fxml;
    exports com.mich.gwan.bookstore.models;
    opens com.mich.gwan.bookstore.models to javafx.fxml;
    exports com.mich.gwan.bookstore.validation;
    opens com.mich.gwan.bookstore.validation to javafx.fxml;
    exports com.mich.gwan.bookstore.controllers.register;
    opens com.mich.gwan.bookstore.controllers.register to javafx.fxml;
}